$(document).ready(function () {

    //1. hide error section
    $("#partCodeError").hide();
    $("#partWidthError").hide();
    $("#partHeightError").hide();
    $("#partLegnthError").hide();
    $("#partCostError").hide();
    $("#partCurrencyError").hide();
    $("#partMusError").hide();
    $("#partDescError").hide();

    //2. define  error variable
    var partCodeError = false;
    var partWidthError = false;
    var partHeightError = false;
    var partLegnthError = false;
    var partCostError = false;
    var partCurrencyError = false;
    var partMusError = false;
    var partDescError = false;

    function validate_partCode() {

        //  Pattern Matching using RegEx
        var exp = /^[A-Z0-9\-\s]{2,20}$/;

        var val = $("#partCode").val();

        if (val == "") {
            $("#partCodeError").show();
            $("#partCodeError").html(" * <b>part code</b> can't be empty");
            $("#partCodeError").css("color", "red");
            partCodeError = false;
        } else if (!exp.test(val)) {
            $("#partCodeError").show();
            $("#partCodeError").html(" * <b>part code</b> must  be 2-20 uppercase letters");
            $("#partCodeError").css("color", "red");
            partCodeError = false;
        } else {
            var id = 0; // for register page
            if ($("#id").val() != undefined) {
                id = $("#id").val(); // for edit page
            }
            $.ajax({
                url: "validatecode", data: {code: val, id: id}, success(resText) {
                    if (resText != "") {
                        $("#partCodeError").show();
                        $("#partCodeError").html(resText);
                        $("#partCodeError").css("color", "red");
                        partCodeError = false;
                    } else {
                        $("#partCodeError").hide();
                        partCodeError = true;
                    }
                },
            });
        }
        return partCodeError;
    }


    function validate_partWidth() {
        partWidthError = validate_numval("partWidth", "partWidthError");
    }

    function validate_partHeight() {
        partHeightError = validate_numval("partHeight", "partHeightError");
    }

    function validate_partLegnth() {
        partLegnthError = validate_numval("partLegnth", "partLegnthError");
    }

    function validate_partCost() {
        partCostError = validate_numval("partCost", "partCostError");
    }

    function validate_numval(id, error) {
        //  Pattern Matching using RegEx
        var exp = /^[0-9.]{1,20}$/;

        var val = $("#" + id).val();

        if (val == "") {
            $("#" + error).show();
            $("#" + error).html("* can't be empty");
            $("#" + error).css("color", "red");
            this.error = false;
        } else if (!exp.test(val)) {
            $("#" + error).show();
            $("#" + error).html(" * invalid entry");
            $("#" + error).css("color", "red");
            error = false;
        } else {
            $("#" + error).hide();
            error = true;
        }
        return error;
    }

    function validate_partCurrency() {
        var val = $("#partCurrency").val();
        if (val == "") {
            $("#partCurrencyError").show();
            $("#partCurrencyError").html(" * please  select one <b>currency</b>");
            $("#partCurrencyError").css("color", "red");
            partCurrencyError = false;
        } else {
            $("#partCurrencyError").hide();
            partCurrencyError = true;
        }
        return partCurrencyError;
    }

    function validate_partMus() {
        var val = $("#partMus").val();
        if (val == "") {
            $("#partMusError").show();
            $("#partMusError").html(" * please  select one <b>mus</b>");
            $("#partMusError").css("color", "red");
            partMusError = false;
        } else {
            $("#partMusError").hide();
            partMusError = true;
        }
        return partMusError;
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

    $("#partWidth").keyup(function () {
        validate_partWidth();
    });

    $("#partHeight").keyup(function () {
        validate_partHeight();
    });

    $("#partLegnth").keyup(function () {
        validate_partLegnth();
    });

    $("#partCost").keyup(function () {
        validate_partCost();
    });

    $('[name="partCurrency"]').change(function () {
        validate_partCurrency();
    });

    $('[name="mus.id"]').change(function () {
        validate_partMus();
    });

    $("#partDesc").keyup(function () {
        validate_partDesc();
    });

    //5. on click form submit
    $("#PartForm").submit(function () {
        validate_partCode();
        validate_partWidth();
        validate_partHeight();
        validate_partLegnth();
        validate_partCost();
        validate_partCurrency();
        validate_partMus();
        validate_partDesc();

        if (partCodeError && partWidthError && partHeightError && partLegnthError && partCostError && partCurrencyError && partMusError && partDescError) {
            return true;
        } else {
            return false;
        }
    });
});
