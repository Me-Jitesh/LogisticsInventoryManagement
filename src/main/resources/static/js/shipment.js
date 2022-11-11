$(document).ready(function () {
    //1. hide error section
    $("#shipModeError").hide();
    $("#shipCodeError").hide();
    $("#enableShipError").hide();
    $("#shipGradError").hide();
    $("#shipDescError").hide();
    //2. define  error variable
    var shipModeError = false;
    var shipCodeError = false;
    var enableShipError = false;
    var shipGradError = false;
    var shipDescError = false;

    //3. define  validate function
    function validate_shipMode() {
        var val = $("#shipMode").val();
        if (val == '') {
            $("#shipModeError").show();
            $("#shipModeError").html(" * Please  select one <b>Mode</b>");
            $("#shipModeError").css('color', 'red');
            shipModeError = false;
        } else {
            $("#shipModeError").hide();
            shipModeError = true;
        }
        return shipModeError;
    }

    function validate_shipCode() {
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
        var val = $("#shipCode").val();
        if (val == '') {
            $("#shipCodeError").show();
            $("#shipCodeError").html(" * <b>Ship Code</b> can't be empty");
            $("#shipCodeError").css('color', 'red');
            shipCodeError = false;
        } else if (!exp.test(val)) {
            $("#shipCodeError").show();
            $("#shipCodeError").html(" * <b>ship code</b> must  be 2-20 uppercase letters");
            $("#shipCodeError").css('color', 'red');
            shipCodeError = false;
        } else {
            var id = 0; // for register page
            if ($("#id").val() != undefined) { // for edit page
                id = $("#id").val();
            }
            $.ajax({
                url: 'validatecode',
                data: {"code": val, "id": id},
                success(resText) {
                    if (resText != "") {
                        $("#shipCodeError").show();
                        $("#shipCodeError").html(resText);
                        $("#shipCodeError").css('color', 'red');
                        shipCodeError = false;
                    } else {
                        $("#shipCodeError").hide();
                        shipCodeError = true;
                    }
                }
            });
        }
        return shipCodeError;
    }

    function validate_enableShip() {
        var len = $('[name="enableShip"]:checked').length;
        if (len == 0) {
            $("#enableShipError").show();
            $("#enableShipError").html(" * Please  Choose <b>Enable Ship</b>");
            $("#enableShipError").css('color', 'red');
            enableShipError = false;
        } else {
            $("#enableShipError").hide();
            enableShipError = true;
        }
        return enableShipError;
    }

    function validate_shipGrad() {
        var len = $('[name="shipGrad"]:checked').length;
        if (len == 0) {
            $("#shipGradError").show();
            $("#shipGradError").html(" * Please  Choose <b>Grad Ship</b>");
            $("#shipGradError").css('color', 'red');
            shipGradError = false;
        } else {
            $("#shipGradError").hide();
            shipGradError = true;
        }
        return shipGradError;
    }

    function validate_shipDesc() {
        var val = $("#shipDesc").val();
        if (val == '') {
            $("#shipDescError").show();
            $("#shipDescError").html(" * Please  Enter <b>Description</b>");
            $("#shipDescError").css('color', 'red');
            shipDescError = false;
        } else {
            $("#shipDescError").hide();
            shipDescError = true;
        }
        return shipDescError;
    }

    //4. link  with event
    $("#shipMode").change(function () {
        validate_shipMode();
    });
    $("#shipCode").keyup(function () {
        // $("#shipCode").val($("#shipCode").val().toUpperCase()); //alternative
        $(this).val($(this).val().toUpperCase());
        validate_shipCode();
    });
    $('[name="enableShip"]').change(function () {
        validate_enableShip();
    });
    $('[name="shipGrad"]').change(function () {
        validate_shipGrad();
    });
    $("#shipDesc").keyup(function () {
        validate_shipDesc();
    });
    //5. on click form submit
    $("#shipmentTypeForm").submit(function () {
        validate_shipMode();
        validate_shipCode();
        validate_enableShip();
        validate_shipGrad();
        validate_shipDesc();
        if (shipModeError && shipCodeError && enableShipError && shipGradError &&
            shipDescError) return true;
        else return false;
    });
});