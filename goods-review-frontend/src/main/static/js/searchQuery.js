function search(query){
    $.get('/search?q=' + query, function(data) {
        console.log(data);
    });
}