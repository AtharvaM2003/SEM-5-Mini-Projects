import spacy
from datetime import datetime, timedelta
import pygame
import tkinter as tk
from tkinter import ttk, simpledialog, messagebox, filedialog
import json
import threading
import speech_recognition as sr
from gtts import gTTS
import os



# Load the English NLP model
nlp = spacy.load('en_core_web_sm')

TASKS_FILE = 'tasks.json'
COMPLETED_TASKS_FILE = 'completed_tasks.json'
completed_tasks = set()
task_data = {}
scheduled_alarms = {}


DEFAULT_SOUND = '/media/amey/New Volume/pythonproject/file_example_MP3_700KB.wav'  # Replace with the path of your default sound file

def play_sound(task, sound_path):
    pygame.mixer.init()
    if sound_choice.get() == "tts":
        tts = gTTS(text=task, lang='en')
        tts.save("task_to_say.mp3")
        pygame.mixer.music.load("task_to_say.mp3")
    else:
        pygame.mixer.music.load(sound_path)
    pygame.mixer.music.play()
    root.after(10000, pygame.mixer.music.stop)


def alarm_triggered(task, sound_type):
    if sound_type == "tts":
        play_sound(task, None)  # None because we don't need a sound path for TTS
    else:
        play_sound(task, task_data[task]['sound_path'])
    decision = messagebox.askyesno("ALARM", f"Time to {task}. Do you want to snooze?")
    if decision:
        pygame.mixer.music.stop()  # Stop the music immediately if snooze is selected
        snooze(task, sound_type)
    else:
        pygame.mixer.music.stop()  # Stop the music if the user selects "No"

    

def snooze(task, sound_type):
    snooze_time = 5 * 1000  # 5 seconds in milliseconds
    root.after(snooze_time, alarm_triggered, task, sound_type)



def handle_alarm(task, priority, sound_path, description):
    doc = nlp(task)
    now = datetime.now()
    alarm_time = None

    for ent in doc.ents:
        if ent.label_ == 'TIME':
            task_time = ent.text
            if 'hour' in task_time:
                delta = int(task_time.split()[0])
                alarm_time = now + timedelta(hours=delta)
            elif 'minute' in task_time:
                delta = int(task_time.split()[0])
                alarm_time = now + timedelta(minutes=delta)
            elif 'second' in task_time:
                delta = int(task_time.split()[0])
                alarm_time = now + timedelta(seconds=delta)
            else:
                return
                
            sound_type = sound_choice.get()
            delay = (alarm_time - now).total_seconds() * 1000
            scheduled_alarms[task] = root.after(int(delay), alarm_triggered, task, sound_path)

            return

        
def save_tasks():
    with open(TASKS_FILE, 'w') as file:
        json.dump(task_data, file)

def load_tasks():
    global task_data
    try:
        with open(TASKS_FILE, 'r') as file:
            task_data = json.load(file)
    except FileNotFoundError:
        task_data = {}

    for i in tasks_treeview.get_children():
     tasks_treeview.delete(i)
    for task, details in task_data.items():
     tasks_treeview.insert("", "end", values=(task, details['priority']))



def remove_task():
    selected_items = tasks_treeview.selection()
    if not selected_items:
        return
    
    for item in selected_items:
        task, priority = tasks_treeview.item(item)["values"]

        # Cancel the alarm if it exists
        if task in scheduled_alarms:
            root.after_cancel(scheduled_alarms[task])
            del scheduled_alarms[task]

        # Delete the task from task_data
        if task in task_data:
            del task_data[task]

        # Remove the task from the Treeview
        tasks_treeview.delete(item)
    
    save_tasks()

def complete_task():
    selected_items = tasks_treeview.selection()
    if not selected_items:
        return
    
    for item in selected_items:
        task, priority = tasks_treeview.item(item)["values"]

        # Cancel the alarm if it exists
        if task in scheduled_alarms:
            root.after_cancel(scheduled_alarms[task])
            del scheduled_alarms[task]

        # Change the color of the task in Treeview
        tasks_treeview.item(item, tags="completed")
        tasks_treeview.tag_configure("completed", background="lightgreen")

        # Delete the task from task_data but keep it in the Treeview
        if task in task_data:
            del task_data[task]

    save_tasks()

def view_description():
    selected_item = tasks_treeview.selection()
    if not selected_item:
        return
    selected_item = selected_item[0]
    task, _ = tasks_treeview.item(selected_item)["values"]

    if task in task_data:
        description = task_data[task]["description"]
        messagebox.showinfo(f"Task Description - {task}", description)


def create_task_entries():
    task_entries.clear()  # Clear the existing task entries list
    description_entries.clear()  # Clear the existing description entries list
    priority_combos.clear()  # Clear the existing priority combos list
   
    for widget in tasks_frame.winfo_children():
        widget.destroy()
    try:
        n = int(num_tasks_var.get())
    except ValueError:
        messagebox.showerror("Error", "Please enter a valid number of tasks.")
        return

    # Add titles/labels for the input columns
    ttk.Label(tasks_frame, text="Task", font=header_font).grid(row=0, column=1, padx=5, pady=2)
    ttk.Label(tasks_frame, text="Description", font=header_font).grid(row=0, column=2, padx=5, pady=2)
    ttk.Label(tasks_frame, text="Priority", font=header_font).grid(row=0, column=3, padx=5, pady=2)

    for i in range(n):
        label = ttk.Label(tasks_frame, text=f"Task {i+1}")
        label.grid(row=i+1, column=0, padx=5, pady=2)  # Adjusted the row index by +1 to account for the added titles
        
        entry = ttk.Entry(tasks_frame)
        entry.grid(row=i+1, column=1, padx=5, pady=2)

        desc_entry = ttk.Entry(tasks_frame)
        desc_entry.grid(row=i+1, column=2, padx=5, pady=2)

        combo = ttk.Combobox(tasks_frame, values=["High", "Medium", "Low"], state="readonly")
        combo.grid(row=i+1, column=3, padx=5, pady=2)  # Adjusted the row index by +1
        combo.set("Medium")
        
        task_entries.append(entry)
        description_entries.append(desc_entry)
        priority_combos.append(combo)





def schedule_tasks():
    # Create lists to store the input values
    tasks = []
    descriptions = []
    priorities = []
    sound_paths = []

    # Extract values from task_entries and other input fields
    for i in range(len(task_entries)):
        task = task_entries[i].get()
        description = description_entries[i].get()
        priority = priority_combos[i].get()

        if not task:
            continue

        tasks.append(task)
        descriptions.append(description)
        priorities.append(priority)

        if sound_choice.get() == "default":
           sound_paths.append(DEFAULT_SOUND)
        elif sound_choice.get() == "custom":
            sound_path = filedialog.askopenfilename(title="Select Sound File", filetypes=(("WAV files", "*.wav"), ("All files", "*.*")))
            if not sound_path:  # if the user cancels the file dialog
                sound_path = DEFAULT_SOUND
            sound_paths.append(sound_path)
    else:  # for Text-to-Speech option
        sound_paths.append(None)  # Append None, since we don't need a sound path for TTS


    # Handle alarms and update the task_data dictionary and listbox
    for i, task in enumerate(tasks):
        handle_alarm(task, priorities[i], sound_paths[i], descriptions[i])
        task_data[task] = {
            "priority": priorities[i],
            "description": descriptions[i],
            "sound_path": sound_paths[i]
        }
    
    save_tasks()
    load_tasks()  # Refresh the listbox

    # Clear the previous entries to allow for new task scheduling
    for entry in task_entries:
        entry.delete(0, tk.END)
    for entry in description_entries:
        entry.delete(0, tk.END)
    for combo in priority_combos:
        combo.set("Medium")




def recognize_speech():
    r = sr.Recognizer()
    with sr.Microphone() as source:
        print("Preparing to capture audio...")  # Add this
        audio = r.listen(source)
        try:
            print("Trying to recognize the captured audio...")  # Add this
            return r.recognize_google(audio).lower()
        except sr.UnknownValueError:
            print("Could not understand audio")
        except sr.RequestError:
            print("API unavailable")
    return ""

def remove_task_by_name(task_name):
    items_to_remove = []
    for item in tasks_treeview.get_children():
        task, _ = tasks_treeview.item(item)["values"]
        if task.lower() == task_name.lower():
            items_to_remove.append(item)
    
    if not items_to_remove:
        print(f"No task named '{task_name}' found.")
        return

    for item in items_to_remove:
        tasks_treeview.delete(item)

    if task_name in task_data:
        del task_data[task_name]
    save_tasks()

def complete_task_by_name(task_name):
    items_to_complete = []
    for item in tasks_treeview.get_children():
        task, _ = tasks_treeview.item(item)["values"]
        if task.lower() == task_name.lower():
            items_to_complete.append(item)
    
    if not items_to_complete:
        print(f"No task named '{task_name}' found.")
        return

    for item in items_to_complete:
        tasks_treeview.item(item, tags="completed")
        tasks_treeview.tag_configure("completed", background="lightgreen")
        tasks_treeview.delete(item)  # or keep it if you prefer

    if task_name in task_data:
        del task_data[task_name]
    save_tasks()


def voice_command():
    while True:
        voice_btn.config(text="Listening...")
        
        command = recognize_speech()
        
        # Display the recognized command in the transcription text widget
        transcription_text.delete(1.0, tk.END)
        transcription_text.insert(tk.END, command)

        voice_btn.config(text="Listen for Voice Command")

        if "set alarm" in command:
            print("Setting an alarm...")
        elif "snooze" in command:
            pygame.mixer.music.stop()
            snooze(command.split()[-1])
        elif "remove" in command:
            task_to_remove = command.replace("remove", "").strip()
            remove_task_by_name(task_to_remove)
        elif "complete" in command:
            task_to_complete = command.replace("complete", "").strip()
            complete_task_by_name(task_to_complete)
        elif "cancel" in command:
            pygame.mixer.music.stop()
            print("Alarm canceled.")
        elif "exit" in command:
            break



# [ ... imports and function declarations ... ]

root = tk.Tk()
root.title("Task Scheduler")
root.geometry("750x650")

# Use consistent font styles
header_font = ("Arial", 14, "bold")
font_style = ("Arial", 12)

# Apply the ttk theme for modern styling
style = ttk.Style()
style.theme_use('clam')

# Create an outer frame that fills the root window
outer_frame = tk.Frame(root, bg='white')
outer_frame.pack(fill=tk.BOTH, expand=True)

# Create the main frame centered inside the outer frame
main_frame = ttk.Frame(outer_frame, padding="20 40",borderwidth=1,relief="solid")
main_frame.place(relx=0.5, rely=0.5, anchor='c')


num_tasks_var = tk.StringVar()

label = ttk.Label(main_frame, text="How many tasks do you want to schedule?", font=header_font)
label.grid(row=0, column=0, pady=15, columnspan=2)

num_tasks_entry = ttk.Entry(main_frame, textvariable=num_tasks_var, font=font_style)
num_tasks_entry.grid(row=0, column=2, pady=15)

generate_btn = ttk.Button(main_frame, text="Generate Task Entries", command=create_task_entries)
generate_btn.grid(row=0, column=3, pady=15, padx=15)

tasks_frame = ttk.Frame(main_frame)
tasks_frame.grid(row=1, column=0, columnspan=4, pady=15, padx=15)

task_entries = []
description_entries = []
priority_combos = []

# Radio buttons for sound choice
sound_choice = tk.StringVar(value="default")
tk.Radiobutton(main_frame, text="Use Default Ring", variable=sound_choice, value="default", font=font_style).grid(row=2, column=0, pady=15)
tk.Radiobutton(main_frame, text="Use Custom Ring", variable=sound_choice, value="custom", font=font_style).grid(row=2, column=1, pady=15)
tk.Radiobutton(main_frame, text="Use Text-to-Speech", variable=sound_choice, value="tts", font=font_style).grid(row=2, column=2, pady=15)


schedule_btn = ttk.Button(main_frame, text="Schedule Tasks", command=schedule_tasks)
schedule_btn.grid(row=3, column=0, columnspan=4, pady=15)

tasks_treeview = ttk.Treeview(main_frame, height=10, columns=("Task", "Priority"), show="headings")
tasks_treeview.grid(row=4, column=0, columnspan=4, pady=15)

# Define the column headers
tasks_treeview.heading("Task", text="Task")
tasks_treeview.heading("Priority", text="Priority")


# Define the column widths (can adjust as necessary)
tasks_treeview.column("Task", width=250)
tasks_treeview.column("Priority", width=100)



remove_btn = ttk.Button(main_frame, text="Remove Task", command=remove_task)
remove_btn.grid(row=5, column=0, pady=15)

complete_btn = ttk.Button(main_frame, text="Mark as Completed", command=complete_task)
complete_btn.grid(row=5, column=1, pady=15)

desc_btn = ttk.Button(main_frame, text="View Description", command=view_description)
desc_btn.grid(row=5, column=2, pady=15)

voice_btn = ttk.Button(main_frame, text="Listen for Voice Command", command=start_voice_command)
voice_btn.grid(row=6, column=0, columnspan=4, pady=15)

# Update the load_tasks and schedule_tasks function to show sequence numbers
transcription_label = ttk.Label(main_frame, text="Voice Command Transcription:", font=header_font)
transcription_label.grid(row=7, column=0, pady=10, columnspan=4, sticky="w")
transcription_text = tk.Text(main_frame, height=4, width=50, font=font_style, wrap=tk.WORD)
transcription_text.grid(row=8, column=0, columnspan=4, pady=10)


root.update_idletasks()



root.mainloop()





