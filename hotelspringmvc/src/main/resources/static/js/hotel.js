const t = new Date();
const date = ('0' + t.getDate()).slice(-2);
const month = ('0' + (t.getMonth() + 1)).slice(-2);
const year = t.getFullYear();


const time = `${date}/${month}/${year}`;
$(document).ready(function () {

    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (lsthotel, status) {
            $('.myModal #id').val(lsthotel.id);
            $('.myModal #name').val(lsthotel.name);
            $('.myModal #name_furigana').val(lsthotel.namefurigana);
            $('.myModal #url').val(lsthotel.url);
            $('.myModal #trade_name').val(lsthotel.tradename);
            $('.myModal #type').val(lsthotel.type);
            $('.myModal #phone_number').val(lsthotel.phonenumber);
            $('.myModal #fax_number').val(lsthotel.faxnumber);
            $('.myModal #pref_code').val(lsthotel.prefcode);
            $('.myModal #postal_code').val(lsthotel.postalcode);
            $('.myModal #address').val(lsthotel.address);
            $('.myModal #building_name').val(lsthotel.buildingname);
            $('.myModal #license_number').val(lsthotel.licensenumber);
            $('.myModal #image').val(lsthotel.image);
            $('.myModal #payment_method').val(lsthotel.paymentmethod);
            $('.myModal #create_at').val(lsthotel.createat);
            $('.myModal #update_at').val(time);
        })
        $('.myModal #exampleModal').modal();
    });
});