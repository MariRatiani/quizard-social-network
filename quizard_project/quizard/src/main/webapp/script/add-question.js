const button = document.getElementById("addQuestion");
const addAnsForMultiAnswer = document.getElementById("add_answer");
const addAnsForMultiChoice = document.getElementById("add-answer-choice");

function myFunction() {
    var types = ['Choose Question Type','Question_Response','Fill_In_The_Blank','Multiple_Choice','Picture_Response', 'Multi_Answer', 'Multiple_Choice_Multiple_Answers'];

    var dropdown_div = document.getElementById("dropdown-div");
    var dropdown = document.createElement("select");
    dropdown.setAttribute("id", "mySelect");

    for (var i = 0; i < types.length; i++) {
        var option = document.createElement("option");
        option.value = types[i];
        option.innerHTML = types[i];
        dropdown.appendChild(option);
    }
    dropdown_div.append(dropdown);
    button.remove();

    var select = document.getElementById("mySelect");
    var blank_div = document.getElementById("fill-in-the-black");
    var question_res_div = document.getElementById("Question-response");
    var multi_Answer_div = document.getElementById("multiple-choice");
    var picture_Response_div = document.getElementById("Picture_Response");
    var multiple_answer_div = document.getElementById("Multiple_Choice_Multiple_Answers");
    var multi_answer_div = document.getElementById("multi-answer");

    select.addEventListener("change", function() {
        const selectedValue = select.value;

        blank_div.style.display = "none";
        question_res_div.style.display = "none";
        multi_Answer_div.style.display = "none";
        picture_Response_div.style.display = "none";
        multiple_answer_div.style.display = "none";
        multi_answer_div.style.display = "none";

        if(selectedValue === 'Question_Response'){
            question_res_div.style.display = "block";
        }

        if (selectedValue === 'Fill_In_The_Blank') {
            blank_div.style.display = "block";
        }

        if(selectedValue === 'Multiple_Choice'){
            multi_Answer_div.style.display = "block";
        }

        if(selectedValue === 'Picture_Response'){
            picture_Response_div.style.display = "block";
        }

        if(selectedValue === 'Multi_Answer'){
            multi_answer_div.style.display = "block";
        }

        if(selectedValue === 'Multiple_Choice_Multiple_Answers'){
            multiple_answer_div.style.display = "block";
        }

    });
}

function addAnswers(divId, ansbutton, addQuestionButton, checkBoxName){
    const my_div = document.getElementById(divId);
    const label = document.createElement("label");

    const checkbox = document.createElement("input");
    checkbox.setAttribute("type", "radio");
    checkbox.setAttribute("name", checkBoxName);

    var textField = document.createElement("input");
    textField.setAttribute("type", "text");
    textField.setAttribute("placeholder", "answer");

    label.append(checkbox);
    label.append(textField);

    var br = document.createElement("br");
    var br2 = document.createElement("br");

    // var toDeleteBr = document.getElementsByName("between_buttons");

    // toDeleteBr.remove();
    ansbutton.remove();
    addQuestionButton.remove();

    my_div.append(label);
    my_div.append(br);
    my_div.append(ansbutton);
    my_div.append(addQuestionButton);
    // my_div.append(br);
}

function addAnswerToMultiAnswer(divId, addAnsbutton, addQuestionButton){
    const my_div = document.getElementById(divId);
    const textField = document.createElement("input");
    textField.setAttribute("type", "text");
    textField.setAttribute("placeholder", "answer");


    var br = document.createElement("br");

    addAnsbutton.remove();
    addQuestionButton.remove();

    my_div.append(textField);
    my_div.append(br);
    my_div.append(addAnsbutton);
    my_div.append(addQuestionButton);
}

var addQ1 = document.getElementById("add-q1");
var addQ2 = document.getElementById("add-q2");


addAnsForMultiChoice.addEventListener("click", function() {
    addAnswers("multiple-choice", addAnsForMultiChoice, addQ1, "checkBoxMultiChoice");
});

addAnsForMultiAnswer.addEventListener("click", function (){
    addAnswerToMultiAnswer("multi-answer", addAnsForMultiAnswer, addQ2);
});

button.addEventListener("click", myFunction);