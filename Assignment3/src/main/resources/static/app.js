var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/endpoint');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': ''}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}
function sendConsultationDetails() {
    var fields = $("#doctorId").val();
    var value= fields;
    fields = $("#patientId").val();
    var value2=fields;
    var date = $("#datec").val();
    stompClient.send("/app/hello", {}, JSON.stringify({'Doctor ID ': value, 'Patient ID: ':value2, 'Date: ':date}));
}

$(function () {
    $("form").on('submit', function (e) {
        //e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#addC").click(function(){
        console.log("Inainte de send");
        sendConsultationDetails();
        console.log("dupa send");
        var fields = $("#patientId").val();
        var value= fields;
        fields = $("#doctorId").val();
        var value2=fields;
        fields= $("#datec").val();
        window.location.href="/addConsultation?doctorId="+value2+"&patientId="+value+"&datec="+fields;
        });

    });



