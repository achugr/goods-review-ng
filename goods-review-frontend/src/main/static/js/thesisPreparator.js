/**
 * Created with IntelliJ IDEA.
 * User: achugr
 * Date: 16.11.12
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */

var thesises = [];
var classificatedThesises = new Array();
var green = "#008000";
var red = "#ff0000";

function contains(a, obj) {
    var i = a.length;
    while (i--) {
        if (a[i] === obj) {
            return true;
        }
    }
    return false;
}

function classifyThesises() {
    var opinionsSet = [];
    for (var i = 0; i < thesises.length; i++) {
        var value = thesises[i].value.split(" ");
        var feature = value[0];
        classificatedThesises[feature] = {};
        classificatedThesises[feature]['opinion']=[];
        classificatedThesises[feature]['sentence']=[];
        opinionsSet[feature] = [];
    }
    for (var i = 0; i < thesises.length; i++) {
        var value = thesises[i].value.split(" ");
        var feature = value[0];
        var opinion = {};
        opinion['content'] = value[1];
        opinion['sentiment'] = thesises[i].sentiment;
//                var opinion = value[1];
//                console.log("content: " + opinion.content + " sentiment: " + opinion.sentiment);
        if(!contains(opinionsSet[feature], opinion.content)){
            classificatedThesises[feature]['opinion'].push(opinion);
            var sentence = findSentenceForThesis(thesises[i].value);
            classificatedThesises[feature]['sentence'].push(sentence);
            opinionsSet[feature].push(opinion.content);
        }
    }
}

function findSentenceForThesis(thesis){
    var commentsContent = document.getElementById("tab1").innerHTML;
    var thesisParts = thesis.split(" ");
    var regexp = new RegExp("\.(.*(" + thesisParts[1] + " " + thesisParts[0] + "|" + thesisParts[0] + " " + thesisParts[1] + ").*)\.", 'g');
    console.log(regexp);
    console.log(thesis);
    var matches = commentsContent.toLowerCase().match(regexp);
    for(index in matches){
        matches[index] = matches[index].replace(/(?:(?:^|\n)\s+|\s+(?:$|\n))/g,'').replace(/\s+/g,' ');
    }
    console.log("matches! : " + matches);
    return matches;
}

function viewThesises() {
    var container = document.getElementById("thesises");
    var staticContainer = document.getElementById("thesisMenu");
    var thesisTabContent = document.getElementById("thesisTabContent");
    for (i in classificatedThesises) {
        console.log("thesis is: " + i);
        $('<div class="row thesisForView"><a name="'+ i + '" id="'+ i +'"href="#activeThesis" class="btn btn-large" style="width:100px;" rel="popover">'+ i + '</a></div>').
            appendTo(container);
        //data-content="' + classificatedThesises[i] +'"
        var opinions = "";
        for(j in classificatedThesises[i]['opinion']){
            var infoStyle;
            if(classificatedThesises[i]['opinion'][j].sentiment >= 0){
                infoStyle = "label label-success";
            } else {
                infoStyle = "label label-important";
            }
            opinions += "<span style='font-size:12pt ; margin-right: 10px; margin-bottom: 10px; margin-top: 10px;' class='"+infoStyle +"'> " + classificatedThesises[i]['opinion'][j].content + " </span>";
        }
        var sentences = "";
        for(j in classificatedThesises[i]['sentence']){
            sentences += classificatedThesises[i]['sentence'][j] + "<br>";
        }
//                console.log("i am here " + classificatedThesises[i].);
        var options = [];
        options['title']=opinions;
        options['html']=true;
        options['content']=sentences;
        $('#' + i).popover(options);
    }
}

function initThesises() {
    var rawThesises = document.getElementsByClassName("thesis");
    for (var i = 0; i < rawThesises.length; i++) {
        console.log(i + ' ' + rawThesises[i].innerHTML);
        thesises.push(JSON.parse(rawThesises[i].innerHTML));
        thesises[i].importance = 1.0;
//                thesises[i].sentiment = ;
    }
    classifyThesises();
    viewThesises();

//            var options = new Array();
//            options["html"]="hello!";
//            $("#test").popover();
//            $("#example").popover();

}

