function requireEmailFieldIfChecked() {
    var emailCheckboxLabel = document.getElementById("howToContactYou-email-label");
    var emailText = document.getElementById("email");
    if (emailCheckboxLabel.classList.contains("is-selected") && emailText.value === "") {
        emailText.required = true;
    }
}

function requirePhoneFieldIfChecked() {
    var phoneCheckboxLabel = document.getElementById("howToContactYou-phoneNumber-label");
    var phoneText = document.getElementById("phoneNumber");
    if (phoneCheckboxLabel.classList.contains("is-selected") && phoneText.value === "") {
        phoneText.required = true;
    }
}

window.onload = function() {
    requireEmailFieldIfChecked();
    requirePhoneFieldIfChecked();
}