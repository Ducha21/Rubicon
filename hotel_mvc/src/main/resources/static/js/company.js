const t = new Date();
const date = ('0' + t.getDate()).slice(-2);
const month = ('0' + (t.getMonth() + 1)).slice(-2);
const year = t.getFullYear();


const time = `${date}/${month}/${year}`;
$(document).ready(function () {

    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (lstcompany, status) {
            $('.myModal #id').val(lstcompany.id);
            $('.myModal #name').val(lstcompany.name);
            $('.myModal #name_furigana').val(lstcompany.nameFurigana);
            $('.myModal #phone_number').val(lstcompany.phoneNumber);
            $('.myModal #email').val(lstcompany.email);
            $('.myModal #pref_code').val(lstcompany.prefCode);
            $('.myModal #address').val(lstcompany.address);
            $('.myModal #postal_code').val(lstcompany.postalCode);
            $('.myModal #building_name').val(lstcompany.buildingName);
            $('.myModal #create_at').val(lstcompany.createAt);
            $('.myModal #update_at').val(lstcompany.updateAt);
        })
        $('.myModal #exampleModal').modal();
    });
});