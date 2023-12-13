

const newQuizzes = document.getElementById("newQ");

newQuizzes.addEventListener("click", function() {
    displayQuizzes("newQuizzes");
});

const popular = document.getElementById("popularQ");

popular.addEventListener("click", function() {
    displayQuizzes("popularQuizzes");
});

const recentTaken = document.getElementById("recentTakingAct");

recentTaken.addEventListener("click", function() {
    displayQuizzes("myRecentTakingActivities");
});

const friendsActivities = document.getElementById("friendsAct");

friendsActivities.addEventListener("click", function() {
    displayQuizzes("freindsActivities");
});

const quizzesByMedocument = document.getElementById("quizzesByMe");

quizzesByMedocument.addEventListener("click", function() {
    displayQuizzes("quizzesCreatedByMe");
});


//container of lists to display, not buttons
const container = document.getElementById("display_quizzes");


//displays quiz
function displayQuizzes(whichListToDisplay) {

    container.innerHTML = "";

    var originalButton = document.getElementById("one_quiz_to_show");
    var copiedButton = originalButton.cloneNode(true);
    copiedButton.style.display = "block";

    var originalButton2 = document.getElementById("one_quiz_to_show");
    var copiedButton2 = originalButton2.cloneNode(true);
    copiedButton2.style.display = "block";

    var originalButton3 = document.getElementById("one_quiz_to_show");
    var copiedButton3 = originalButton3.cloneNode(true);
    copiedButton3.style.display = "block";

    //es sachveneblad aris sami button. magis magvrad whichListToDisplay am
    //lists gadauyevi da shesabamisi infoebi dabechde. chveulebrivi
    //for-ic aris javascript-shi.
    // copiedButton.innerHTML = aq html rac unda iyos, yvelaferi chawere, mag
    //fquizis saxeli, kategoria, da ro daacher mag qvizze unda gadadiodes

    container.appendChild(copiedButton);
    container.append(copiedButton2);
    container.append(copiedButton3);
}



window.onload = function() {
    displayQuizzes("popularQuizzes");
};
