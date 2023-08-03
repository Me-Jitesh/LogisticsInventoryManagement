$(document).ready(function () {
    $("#passwordError").hide();
    $("#rePasswordError").hide();

    var passwordError = false;
    var rePasswordError = false;

    function validate_password() {
        var exp = /^[A-Za-z0-9\-.@]{6,15}$/;
        var val = $("#password").val();
        if (val == "") {
            $("#passwordError").show();
            $("#passwordError").html(" * required");
            $("#passwordError").css("color", "red");
            passwordError = false;
        } else if (!exp.test(val)) {
            $("#passwordError").show();
            $("#passwordError").html(" * must  be 6-15 letters and . - @ only");
            $("#passwordError").css("color", "red");
            passwordError = false;
        } else {
            $("#passwordError").hide();
            passwordError = true;
        }
        return passwordError;
    }

    function validate_repassword() {
        var pass = $("#password").val();
        var repass = $("#rePassword").val();
        if (pass != repass) {
            $("#rePasswordError").show();
            $("#rePasswordError").html(" * password not mathing");
            $("#rePasswordError").css("color", "red");
            rePasswordError = false;
        } else {
            $("#rePasswordError").hide();
            rePasswordError = true;
        }
        return rePasswordError;
    }

    $("#password").keyup(function () {
        validate_password();
    });

    $("#rePassword").keyup(function () {
        validate_repassword();
    });

    $("#customPwdForm").submit(function () {
        validate_password();
        validate_repassword();
        if (passwordError && rePasswordError) {
            return true;
        } else {
            return false;
        }
    });
});
