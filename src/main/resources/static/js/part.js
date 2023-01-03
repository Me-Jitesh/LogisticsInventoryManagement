$(document).ready(function () {

    //1. hide error section
    $("#partCodeError").hide();
    $("#partCurrencyError").hide();
    $("#partDescError").hide();

    //2. define  error variable
    var partCodeError = false;
    var partCurrencyError = false;
    var partDescError = false;

    function validate_partCode() {

        //  Pattern Matching using RegEx
        var exp = /^[A-Z0-9\-\s]{2,20}$/

        var val = $("#partCode").val();

        if (val == '') {
            $("#partCodeError").show();
            $("#partCodeError").html(" * <b>part code</b> can't be empty");
            $("#partCodeError").css('color', 'red');
            partCodeError = false;
        } else if (!exp.test(val)) {
            $("#partCodeError").show();
            $("#partCodeError").html(" * <b>part code</b> must  be 2-20 uppercase letters");
            $("#partCodeError").css('color', 'red');
            partCodeError = false;
        } else {
            var id = 0;     // for register page
            if ($("#id").val() != undefined) {
                id = $("#id").val();    // for edit page
            }
            $.ajax({
                url: 'validatecode',
                data: {"code": val, "id": id},
                success(resText) {
                    if (resText != "") {
                        $("#partCodeError").show();
                        $("#partCodeError").html(resText);
                        $("#partCodeError").css('color', 'red');
                        partCodeError = false;
                    } else {
                        $("#partCodeError").hide();
                        partCodeError = true;
                    }
                }
            });
        }
        return partCodeError;
    }

    function validate_partCurrency() {
        var val = $("#partCurrency").val();
        if (val == '') {
            $("#partCurrencyError").show();
            $("#partCurrencyError").html(" * please  select one <b>type</b>");
            $("#partCurrencyError").css('color', 'red');
            partCurrencyError = false;
        } else {
            $("#partCurrencyError").hide();
            partCurrencyError = true;
        }
        return partCurrencyError;
    }

    function validate_partDesc() {

        var exp = /^[A-Za-z0-9\s-.*,@:]{2,20}$/;

        var val = $("#partDesc").val();

        if (val == "") {
            $("#partDescError").show();
            $("#partDescError").html(" * please  enter <b>desc</b>");
            $("#partDescError").css("color", "red");
            partDescError = false;
        } else if (!exp.test(val)) {
            $("#partDescError").show();
            $("#partDescError").html(" * <b>part desc </b> must  be 2-100 letters");
            $("#partDescError").css("color", "red");
            partDescError = false;
        } else {
            $("#partDescError").hide();
            partDescError = true;
        }
        return partDescError;
    }

    //4. link  with event

    $("#partCode").keyup(function () {
        // $("#partCode").val($("#partCode").val().toUpperCase()); //alternative
        $(this).val($(this).val().toUpperCase());
        validate_partCode();
    });

    $('[name="partCurrency"]').change(function () {
        validate_partCurrency();
    });

    $("#partDesc").keyup(function () {
        validate_partDesc();
    });

    //5. on click form submit
    $("#PartForm").submit(function () {
        validate_partCode();
        validate_partCurrency();
        validate_partDesc();

        if (partCodeError && partCurrencyError && partDescError) {
            return true;
        } else {
            return false;
        }
    });
});