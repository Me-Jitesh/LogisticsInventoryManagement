$(document).ready(function () {

    $("#docDataError").hide();

    let docDataError = false;

    function validate_docRequired() {
        let val = $("#docData").val();
        if (val == '') {
            $("#docDataError").show();
            $("#docDataError").html(" * file required");
            $("#docDataError").css("color", "red");
            docDataError = false;
        }
        return docDataError;
    }

    function validate_doc(file) {
        let fileName = file.name;
        let fExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        let allowedExt = ["png", "jpg", "jpeg", "txt", "doc", "docx", "pdf"];
        let fileSize = file.size;

        if ($.inArray(fExtension, allowedExt) == -1) {
            $("#docDataError").show();
            $("#docDataError").html(" * allowed only " + allowedExt);
            $("#docDataError").css("color", "red");
            docDataError = false;
        } else if (fileSize < 1024 * 10) {//10kb
            $("#docDataError").show();
            $("#docDataError").html(" * allowed min size 10kb");
            $("#docDataError").css("color", "red");
            docDataError = false;
        } else if (fileSize > 1024 * 1000) {//1 mb
            $("#docDataError").show();
            $("#docDataError").html(" * allowed max size 1mb");
            $("#docDataError").css("color", "red");
            docDataError = false;
        } else {
            $("#docDataError").hide();
            docDataError = true;
        }
        return docDataError;
    }

    $("input[type='file'][name='docData']").change(function () {
        validate_doc(this.files[0]);
    });

    $("#DocForm").submit(function () {
        validate_docRequired();
        if (docDataError) return true; else return false;
    });
});