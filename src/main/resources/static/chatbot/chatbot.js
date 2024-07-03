/**
 * 
 */
//var sock = new SockJS('/ws-green-bot');
var client;
var key;

// 브라우저가 WebSocket을 지원하는지 확인하는 함수
function isWebSocketSupported() {
    return 'WebSocket' in window;
}

// WebSocket 지원 여부를 출력
if (isWebSocketSupported()) {
    console.log("이 브라우저는 WebSocket을 지원합니다.");
} else {
    console.log("이 브라우저는 WebSocket을 지원하지 않습니다.");
}

function connect() {
	client = Stomp.over(new SockJS('/ws-green-bot'));
	client.connect({}, (frame) => {
		key = new Date().getTime();
		//console.log(frame)
		//구독설정
		client.subscribe(`/top/question/${key}`, (answer) => {
			var msgObj = answer.body;
			console.log("msg:", msgObj);
		});
		/*
		var data={
			key: key,
			content: "안녕하세요",
			name: "guest"
		}
		//접속하자마자 연결시도
		client.send("/bot/question",{},JSON.stringify(data));
		*/
	})
}

function disconnect() {
	client.disconnect(() => {
		console.log("Disconnected...")
	});
}

function btnCloseClicked() {
	$("#bot-container").hide();
	//대화창 리셋
	$("#chat-content").html("");
	disconnect();

}
function btnBotClicked() {
	//1. 소켓 접속
	$("#bot-container").show();
	connect()
}

$(function() {
	$("#btn-bot").click(btnBotClicked);
});