$(document).ready(function () {

    //1. hide error section
    $("#orderCodeError").hide();
    $("#refNumberError").hide();
    $("#shipCodeError").hide();
    $("#qltyCheckError").hide();
    $("#poDescError").hide();

    //2. define  error variable
    let orderCodeError = false;
    let refNumberError = false;
    let shipCodeError = false;
    let qltyCheckError = false;
    let poDescError = false;

    function validate_orderCode() {

        //  Pattern Matching using RegEx
        let exp = /^[A-Z0-9\-\s]{2,20}$/;

        let val = $("#orderCode").val();

        if (val == "") {
            $("#orderCodeError").show();
            $("#orderCodeError").html(" * required");
            $("#orderCodeError").css("color", "red");
            orderCodeError = false;
        } else if (!exp.test(val)) {
            $("#orderCodeError").show();
            $("#orderCodeError").html(" * <b>order code</b> must  be 2-20 uppercase letters");
            $("#orderCodeError").css("color", "red");
            orderCodeError = false;
        } else {
            let id = 0; // for register page
            if ($("#id").val() != undefined) {
                id = $("#id").val(); // for edit page
            }
            $.ajax({
                url: "validatecode", data: {code: val, id: id}, success(resText) {
                    if (resText != "") {
                        $("#orderCodeError").show();
                        $("#orderCodeError").html(resText);
                        $("#orderCodeError").css("color", "red");
                        orderCodeError = false;
                    } else {
                        $("#orderCodeError").hide();
                        orderCodeError = true;
                    }
                },
            });
        }
        return orderCodeError;
    }

    function validate_refNumber() {
        let val = $("#refNumber").val();
        if (val == '') {
            $("#refNumberError").show();
            $("#refNumberError").html(" * required");
            $("#refNumberError").css("color", "red");
            refNumberError = false;
        } else {
            $("#refNumberError").hide();
            refNumberError = true;
        }
        return refNumberError;
    }

    function validate_shipCode() {
        let val = $("#shipCode").val();
        if (val == '') {
            $("#shipCodeError").show();
            $("#shipCodeError").html("* required");
            $("#shipCodeError").css('color', 'red');
            shipCodeError = false;
        } else {
            $("#shipCodeError").hide();
            shipCodeError = true;
        }
        return shipCodeError;
    }

    function validate_qltyCheck() {
        let len = $('[name="qltyCheck"]:checked').length;
        if (len == 0) {
            $("#qltyCheckError").show();
            $("#qltyCheckError").html("* required");
            $("#qltyCheckError").css('color', 'red');
            qltyCheckError = false;
        } else {
            $("#qltyCheckError").hide();
            qltyCheckError = true;
        }
        return qltyCheckError;
    }

    function validate_poDesc() {
        let exp = /^[A-Za-z0-9\s-.*,@:]{2,100}$/;
        let val = $("#poDesc").val();

        if (val == "") {
            $("#poDescError").show();
            $("#poDescError").html(" * required");
            $("#poDescError").css("color", "red");
            poDescError = false;
        } else if (!exp.test(val)) {
            $("#poDescError").show();
            $("#poDescError").html(" * <b> desc </b> must  be 2-100 letters");
            $("#poDescError").css("color", "red");
            poDescError = false;
        } else {
            $("#poDescError").hide();
            poDescError = true;
        }
        return poDescError;
    }

    //4. link  with event
    $("#orderCode").keyup(function () {
        $(this).val($(this).val().toUpperCase());
        validate_orderCode();
    });

    $("#refNumber").keyup(function () {
        $(this).val($(this).val().toUpperCase());
        validate_refNumber();
    });

    $('[name="st.id"]').change(function () {
        validate_shipCode();
    });

    $('[name="qltyCheck"]').change(function () {
        validate_qltyCheck();
    });

    $("#poDesc").keyup(function () {
        validate_poDesc();
    });

    //5. on click form submit
    $("#PoForm").submit(function () {
        validate_orderCode();
        validate_refNumber();
        validate_shipCode();
        validate_qltyCheck();
        validate_poDesc();

        if (orderCodeError && refNumberError && shipCodeError && qltyCheckError && poDescError) {
            return true;
        } else {
            return false;
        }
    });
});
