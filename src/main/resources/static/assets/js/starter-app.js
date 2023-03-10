function selectCheckboxFieldIfTextFieldIsNotEmpty(textFieldId, checkboxFieldId) {
    let textField = document.getElementById(textFieldId);
    let checkboxField = document.getElementById(checkboxFieldId);
    textField.addEventListener("input", function() {
        if (textField.value !== "") {
            checkboxField.checked = true;
            checkboxField.parentElement.classList.add("is-selected");
        }  else {
            checkboxField.checked = false;
            checkboxField.parentElement.classList.remove("is-selected");
        }
    });
}

window.onload = function() {
    selectCheckboxFieldIfTextFieldIsNotEmpty("email", "howToContactYou-email");
    selectCheckboxFieldIfTextFieldIsNotEmpty("phoneNumber", "howToContactYou-phoneNumber");
}