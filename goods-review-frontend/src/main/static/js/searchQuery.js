function search(query){
    $.get('/search?q=' + query, function(data) {
        console.log(data);
    });
}

function processLeftMenu(){
    $('#navi_ul li').click(function(){ $('#navi_ul li').removeClass('active'); $(this).addClass('active'); });
}