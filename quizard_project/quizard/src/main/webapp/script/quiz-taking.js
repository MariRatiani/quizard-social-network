//
// window.onload = function() {
//     //AQ UNDA CHAWERO LOGIKA KONKRETULI QVIZIS TIPIS WAMOGEBIS
//     //DA QVEMOT ES FUNQCIA GAMOIDZAXO SHESABAMISI TIPIS ID-TI
//     //daaxloebit ase ra
//     // displayQuestion("multi-choice");
//     // displayQuestion("multi-choice");
// };
//
// function displayQuestion(question_type){
//
//     const originalDiv = document.getElementById(question_type);
//     const container = document.getElementById("displayQuestions");
//
//     // Clone the original div
//     const copiedDiv = originalDiv.cloneNode(true);
//
//     //AQ SACHIROA DEFAULT FIELDS RAC MAQVS NAMDVILI KITXVEBIT
//     //DA SACHIRO AMBEBIT CHAANACVLO
//     copiedDiv.style.display = "block";
//
//     // Add the copied div to the container
//     container.appendChild(copiedDiv);
// }
//
//
//

{
    "globals"
:
    {
        "$"
    :
        false
    }
}

$(document).ready(function () {
    $('#answer').blur(function () {
        $.ajax({
            url: 'ImmediateCorrectionServlet', data: {
                answer: $('#answer').val()
            }, success: function (responseText) {
                $('#ajaxImmediateCorrectionResponse').text(responseText);
            }
        });
    });
});