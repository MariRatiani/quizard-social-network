const searchButton = document.getElementById("searchButton");
const searchInput = document.getElementById("searchInput");

searchButton.addEventListener("click", function() {
    const searchTerm = searchInput.value;
    displayQuiz(searchTerm);
});

//shiedzleba sachiro gaxdes am funqcias rame ro gadascet
//ar vici back-is nawili javascript-shi rogor iwereba :(

//displays searched quiz
function displayQuiz(searchQuery) {
    const containerOfQuizList = document.getElementById("display_quizzes");
    containerOfQuizList.remove();
    const originalButton = document.getElementById("quiz_info");
    const container = document.getElementById("list");

    // Clone the original div
    const copiedButton = originalButton.cloneNode(true);
    // SACHIRO AMBEBIT CHAANACVLE
    copiedButton.style.display = "block";
    var quiz_name = document.createElement("p");
    quiz_name.innerHTML = "WRITE HERE REAL QUIZ NAME: " + "QUIZ_CATEGORY: "
    copiedButton.append(quiz_name);
    container.append(copiedButton);
}


window.onload = function() {
    displayAllQuizzes();
};



function displayAllQuizzes(){
    const originalButton = document.getElementById("one_quiz");
    const container = document.getElementById("display_quizzes");
    // Clone the original div
    const copiedButton= originalButton.cloneNode(true);

    copiedButton.style.display = "block";
    //aq chaamatet namdvili info quiz-ze
    container.append(copiedButton);
}




