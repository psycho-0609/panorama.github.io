Array.from(document.querySelectorAll('[data-expand]'), (input) => {
    let parent = input.parentNode;
    function updateSize() {
        parent.dataset.value = input.value
    }
    input.addEventListener('input', updateSize);
    updateSize();
});

$('.special #make-a-grade').click(function (e) {
    e.preventDefault();
    if ($('.grade').css('display') === 'block') {
        $('.grade').hide(500);
    }
    else $('.grade').show(500);
});
mouse_is_inside_grade = false;
$('.grade').hover(function () {
    mouse_is_inside_grade = true;
}, function () {
    mouse_is_inside_grade = false;
});

$('#make-a-grade').hover(function() {
    mouse_is_inside_btn_grade = true;
}, function () {
    mouse_is_inside_btn_grade = false;
})

$("body").mouseup(function () {
    if (!mouse_is_inside_grade && !mouse_is_inside_btn_grade && ($('#make-a-grade').css('display') == 'block')) $(".grade").hide(500);
});

$('#disable').click(function(e) {
    // e.preventDefault();
    $('#make-a-grade').hide(500);
    $(this).hide(500);
    $('#active').show(500);
    $('.grade').css('transform', 'none');
    $('#status').text('Disable');
});

$('#active').click(function(e) {
    // e.preventDefault();
    $('#make-a-grade').hide(500);
    $(this).hide(500);
    $('#disable').show(500);
    $('.grade').css('transform', 'none');
    $('#status').text('Active');
});