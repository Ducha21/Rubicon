// $(document).ready(function)() {
//
//     $('.table . eBtn').on('click', function(event){
//
//
//         $('.myModal #exampleModal').modal();
//     });
// });

    const t = new Date();
    const date = ('0' + t.getDate()).slice(-2);
    const month = ('0' + (t.getMonth() + 1)).slice(-2);
    const year = t.getFullYear();


    const time = `${date}/${month}/${year}`;
    $(document).ready(function () {

    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (lstUser, status) {
            $('.myModal #id1').val(lstUser.id);
            $('.myModal #email1').val(lstUser.email);
            $('.myModal #password1').val(lstUser.password);
            $('.myModal #create_at1').val(lstUser.createAt);
            $('.myModal #update_at1').val(time);
        })
        $('.myModal #exampleModal').modal();
    });
});
