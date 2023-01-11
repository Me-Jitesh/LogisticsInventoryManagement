$(document).ready(function () {
    //1. hide error section
    $("#userTypeError").hide();
    $("#userCodeError").hide();
    $("#userEmailError").hide();
    $("#userContactError").hide();
    $("#userIdTypeError").hide();
    $("#otherDiv").hide();
    $("#ifOtherError").hide();
    $("#ifOtherError").hide();
    $("#userIdNumberError").hide();

    //2. define  error variable
    var userTypeError = false;
    var userCodeError = false;
    var userEmailError = false;
    var userContactError = false;
    var userIdTypeError = false;
    var ifOtherError = true;
    var userIdNumberError = false;

    //3. define  validate function
    function validate_userType() {
        var len = $('[name="userType"]:checked').length;
        if (len == 0) {
            $("#userTypeError").show();
            $("#userTypeError").html(" * please  choose <b>type</b>");
            $("#userTypeError").css('color', 'red');
            userTypeError = false;
        } else {
            $("#userTypeError").hide();
            userTypeError = true;
        }
    }

    function validate_userCode() {
        //  Pattern Matching using RegEx
        /*
        /^ = Opening
        $/ = Closing
         A-Z = allowing uppercase letters only
         0-9 = allowing numbers
         \- = allowing hyphen symbol
         \s = allowing spaces
         {2-20} = String length should be 2 to 20
         */
        var exp = /^[A-Z0-9\-\s]{2,20}$/
        var val = $("#userCode").val();
        if (val == '') {
            $("#userCodeError").show();
            $("#userCodeError").html(" * <b>code</b> can't be empty");
            $("#userCodeError").css('color', 'red');
            userCodeError = false;
        } else if (!exp.test(val)) {
            $("#userCodeError").show();
            $("#userCodeError").html(" * <b>code</b> must  be 2-20 uppercase letters");
            $("#userCodeError").css('color', 'red');
            userCodeError = false;
        } else {
            var id = 0; // for register page
            if ($("#id").val() != undefined) { // for edit page
                id = $("#id").val();
            }
            $.ajax({
                url: 'validatecode', data: {"code": val, "id": id}, success(resText) {
                    if (resText != "") {
                        $("#userCodeError").show();
                        $("#userCodeError").html(resText);
                        $("#userCodeError").css('color', 'red');
                        userCodeError = false;
                    } else {
                        $("#userCodeError").hide();
                        userCodeError = true;
                    }
                }
            });
        }
    }

    function FillUserFor() {
        let val = $('input[name="userType"]:checked').val();
        if (val === "Vendor") {
            $("#userFor").val("Purchase")
        } else if (val === "Customer") {
            $("#userFor").val("Sale")
        }
    }

    function validate_userEmail() {
        let exp = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        var val = $("#userEmail").val();
        if (val == '') {
            $("#userEmailError").show();
            $("#userEmailError").html(" * <b>email</b> required");
            $("#userEmailError").css('color', 'red');
            userEmailError = false;
        } else if (!exp.test(val)) {
            $("#userEmailError").show();
            $("#userEmailError").html(" * enter valid email");
            $("#userEmailError").css('color', 'red');
            userEmailError = false;
        } else {
            var id = 0; // for register page
            if ($("#id").val() != undefined) { // for edit page
                id = $("#id").val();
            }
            $.ajax({
                url: 'validatemail', data: {"email": val, "id": id}, success(resText) {
                    if (resText != "") {
                        $("#userEmailError").show();
                        $("#userEmailError").html(resText);
                        $("#userEmailError").css('color', 'red');
                        userEmailError = false;
                    } else {
                        $("#userEmailError").hide();
                        userEmailError = true;
                    }
                }
            });
        }
    }

    function validate_userContact() {
        var exp = /^[0-9]{10}$/;
        let val = $("#userContact").val();

        if (val == "") {
            $("#userContactError").show();
            $("#userContactError").html(" * required");
            $("#userContactError").css("color", "red");
            userContactError = false;
        } else if (!exp.test(val)) {
            $("#userContactError").show();
            $("#userContactError").html(" * enter 10 digit number only");
            $("#userContactError").css("color", "red");
            userContactError = false;
        } else {
            $("#userContactError").hide();
            userContactError = true;
        }
    }

    function validate_userIdType() {
        let val = $("#userIdType").val();
        if (val === '') {
            $("#userIdTypeError").show();
            $("#userIdTypeError").html(" * please  select one <b>type</b>");
            $("#userIdTypeError").css('color', 'red');
            userTypeError = false;
        } else if (val === "OTHER") {
            $("#otherDiv").show();
            $("#ifOtherError").show();
            $("#ifOtherError").html(" * please enter other ID Name");
            $("#ifOtherError").css('color', 'red');
            ifOtherError = false;
            $("#userIdTypeError").hide();
            userIdTypeError = true;
        } else {
            $("#userIdTypeError").hide();
            userIdTypeError = true;
        }
    }


    function validate_other_id() {

        var exp = /^[A-Z\s]{4,20}$/;
        var val = $("#ifOther").val();

        if (val == "") {
            $("#ifOtherError").show();
            $("#ifOtherError").html(" * required");
            $("#ifOtherError").css("color", "red");
            ifOtherError = false;
        } else if (!exp.test(val)) {
            $("#ifOtherError").show();
            $("#ifOtherError").html(" * enter valid ID");
            $("#ifOtherError").css("color", "red");
            ifOtherError = false;
        } else {
            $("#ifOtherError").hide();
            ifOtherError = true;
        }
    }


    function validate_id_number() {

        var exp = /^[A-Za-z0-9\s-]{4,20}$/;
        var val = $("#userIdNumber").val();

        if (val == "") {
            $("#userIdNumberError").show();
            $("#userIdNumberError").html(" * please enter <b>id</b>");
            $("#userIdNumberError").css("color", "red");
            userIdNumberError = false;
        } else if (!exp.test(val)) {
            $("#userIdNumberError").show();
            $("#userIdNumberError").html(" * <b>id</b> must  be 4-20 letters");
            $("#userIdNumberError").css("color", "red");
            userIdNumberError = false;
        } else {
            $("#userIdNumberError").hide();
            userIdNumberError = true;
        }
    }

    //4. link  with event
    $('[name="userType"]').change(function () {
        validate_userType();
        FillUserFor();
    });

    $("#userCode").keyup(function () {
        $(this).val($(this).val().toUpperCase());
        validate_userCode();
    });

    $("#userEmail").keyup(function () {
        validate_userEmail();
    });

    $("#userContact").keyup(function () {
        validate_userContact();
    });

    $("#userIdType").change(function () {
        validate_userIdType();
    });

    $("#ifOther").keyup(function () {
        validate_other_id();
    });

    $("#userIdNumber").keyup(function () {
        validate_id_number();
    });

    //5. on click form submit
    $("#ProductUserForm").submit(function () {
        validate_userType();
        validate_userCode();
        validate_userEmail();
        validate_userContact();
        if (userIdTypeError == false || ifOtherError == false) {
            validate_userIdType();
        }
        validate_id_number();
        if (userTypeError && userCodeError && userEmailError && userContactError && userIdTypeError && ifOtherError && userIdNumberError) return true; else return false;
    });
});