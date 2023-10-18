const content = document.querySelector(".content");
const btn = document.querySelector(".talk");

const recipes = {
  "butter chicken": {
    introduction: "Let's make delicious Butter Chicken today.",
    steps: [
      "Step 1: Marinate chicken with yogurt, spices, and lemon juice for 2 hours.",
      "Step 2: Heat oil in a pan and sauté onions, garlic, and ginger until fragrant.",
      "Step 3: Add tomato puree and cook until the oil separates.",
      "Step 4: Add marinated chicken and cook until it's no longer pink.",
      "Step 5: Stir in cream, butter, and garam masala. Simmer for 10 minutes.",
      "Step 6: Garnish with cilantro and serve hot with naan or rice.",
    ],
  },
  "biryani": {
    introduction: "How about preparing a flavorful Biryani?",
    steps: [
      "Step 1: Soak basmati rice for 30 minutes.",
      "Step 2: Cook rice with spices and saffron until half-cooked.",
      "Step 3: Layer rice with marinated meat and cook in a covered pot until done.",
      "Step 4: Garnish with fried onions and serve hot.",
    ],
  },
  "paneer tikka": {
    introduction: "Let's cook some Paneer Tikka.",
    steps: [
      "Step 1: Marinate paneer cubes with yogurt and spices.",
      "Step 2: Thread the paneer onto skewers and grill until charred.",
      "Step 3: Serve with mint chutney and lemon wedges.",
    ],
  },
  "chole bhature": {
    introduction: "How about Chole Bhature for lunch?",
    steps: [
      "Step 1: Soak chickpeas overnight and cook with spices.",
      "Step 2: Make a spicy tomato gravy with onions and spices.",
      "Step 3: Serve hot with deep-fried bhature.",
    ],
  },
  "samosa": {
    introduction: "Let's make crispy Samosas.",
    steps: [
      "Step 1: Prepare a spiced potato and pea filling.",
      "Step 2: Make samosa dough and shape it into triangles.",
      "Step 3: Fill each triangle with the potato mixture and deep-fry until golden.",
    ],
  },
};

const hello = ["Hello, how can I assist you with Indian recipes?"];

const SpeechRecognition =
  window.SpeechRecognition || window.webkitSpeechRecognition;
const recognition = new SpeechRecognition();

recognition.onresult = function (event) {
  const current = event.resultIndex;
  const transcript = event.results[current][0].transcript;
  content.textContent = transcript;
  readOutLoud(transcript);
};

btn.addEventListener("click", () => {
  recognition.start();
});

function readOutLoud(message) {
  const speech = new SpeechSynthesisUtterance();
  speech.text = "I am not able to understand, sir";

  if (message.includes("Hey") || message.includes("Hello")) {
    const finalText = hello[0];
    speech.text = finalText;
  } else {
    const recipeName = Object.keys(recipes).find((name) =>
      message.toLowerCase().includes(name)
    );

    if (recipeName) {
      const recipe = recipes[recipeName];
      const finalText = `${recipe.introduction} Here are the steps to make ${recipeName}: ${recipe.steps.join(
        " "
      )}`;
      speech.text = finalText;
    } else {
      const finalText = "Sorry, I am not able to assist with that recipe.";
      speech.text = finalText;
    }
  }

  speech.volume = 1;
  speech.rate = 1.1;
  speech.pitch = 1;

  window.speechSynthesis.speak(speech);
}
