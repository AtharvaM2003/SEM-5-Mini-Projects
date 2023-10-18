sudo apt-get install python3-venv
python3 -m venv myenv
source myenv/bin/activate
pip install spacy
pip install speech_recognition



# Create a new virtual environment
python3 -m venv myenv

# Activate the virtual environment
source myenv/bin/activate

# Now you can install packages using pip
pip install spacy


sudo apt install python3-spacy


# Install pipx
sudo apt install pipx

# Use pipx to install a Python tool
pipx install some-python-tool
x
#deactivate  # exit the current virtual environment
rm -rf myenv  # remove the existing virtual environment
python3 -m venv myenv  # create a new virtual environment
source myenv/bin/activate  # activate the new virtual environment
pip install spacy
pip install pygame
pip install SpeechRecognition
pip install gtts

python -m spacy download en_core_web_sm  # if required