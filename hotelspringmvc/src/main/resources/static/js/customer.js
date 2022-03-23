const t = new Date();
const date = ('0' + t.getDate()).slice(-2);
const month = ('0' + (t.getMonth() + 1)).slice(-2);
const year = t.getFullYear();


const time = `${date}/${month}/${year}`;
$(document).ready(function () {

    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (lstcustomers, status) {
            $('.myModal #id').val(lstcustomers.id);
            $('.myModal #first_name').val(lstcustomers.firstName);
            $('.myModal #first_name_furigana').val(lstcustomers.firstNameFurigana);
            $('.myModal #last_name').val(lstcustomers.lastName);
            $('.myModal #last_name_furigana').val(lstcustomers.lastNameFurigana);
            $('.myModal #postal_code').val(lstcustomers.postalCode);
            $('.myModal #country').val(lstcustomers.country);
            $('.myModal #pref_code').val(lstcustomers.prefCode);
            $('.myModal #address').val(lstcustomers.address);
            $('.myModal #building_name').val(lstcustomers.buildingName);
            $('.myModal #work_place').val(lstcustomers.workPlace);
            $('.myModal #email').val(lstcustomers.email);
            $('.myModal #phone_number').val(lstcustomers.phoneNumber);
            $('.myModal #birthday').val(lstcustomers.birthday);
            $('.myModal #gender').val(lstcustomers.gender);
            $('.myModal #family_phone').val(lstcustomers.familyPhone);
            $('.myModal #create_at').val(lstcustomers.createAt);
            $('.myModal #update_at').val(time);
        })
        $('.myModal #exampleModal').modal();
    });
});