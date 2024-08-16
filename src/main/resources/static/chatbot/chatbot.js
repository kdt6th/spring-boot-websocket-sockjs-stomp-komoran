/**
 * 
 */
//var sock = new SockJS('/ws-green-bot');
var client;
var key;
let flag = false;

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
function formatTime(now){
	
	var ampm = (now.getHours() > 11)?"오후":"오전";
	var hour = now.getHours()%12;
	if(hour==0)hour = 12;
	var minute = now.getMinutes();
	var formattedMinute = String(minute).padStart(2, '0');
	return `${ampm} ${hour}:${formattedMinute}`;
}
function formatDate(now){
	const year = now.getFullYear();
	const month = now.getMonth()+1; //월정보는 0월부터 시작하기 때문에 +1 해줘야함
	const date = now.getDate();
	
	//일:0, 월:1, 화:2 ~ 토:6
	const dayOfWeek = now.getDay(); 
	const days = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
	
	return `${year}년 ${month}월 ${date}일 ${days[dayOfWeek]}`;
}

//대화 내용 추가
function showMessage(tag){
	$("#chat-content").append(tag)
	//스크롤을 제일 아래로
	$("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}

function generateUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}
function generateUniqueKey(){
	//숫자를 36진법 문자열로 변환:36진법은 0-9와 a-z를 사용합니다 (총 36개 문자)
	//console.log("random:",Math.random().toString(36));
	return new Date().getTime().toString(36)+Math.random().toString(36).substring(2);//0.
}

//웹소켓 연결 후 인사말 출력
function connect() {
	client = Stomp.over(new SockJS('/ws-green-bot'));
	client.connect({}, (frame) => {
		key = generateUniqueKey();
		//console.log(frame)
		
		//메시지 수신을위한 구독설정
		client.subscribe(`/topic/bot/${key}`, (answer) => {
			var msgObj = answer.body;
			//console.log("msg:", msgObj);
			//console.log("answer:", answer);
			var now = new Date();
			var time = formatTime(now);
			var date = formatDate(now);
			var tag = `<div class="flex center date">${date}</div>
						<div class="msg bot flex">
							<div class="icon">
								<img src="/images/icon/robot-solid.svg">
							</div>
							<div class="message">
								<div class="part">
									<p>${msgObj}</p>
								</div>
								<div class="time">${time}</div>
							</div>
						</div>
						`;
			
			showMessage(tag);
		});
		
		//* JSON(자바스크립트 객체 표현식) > 이걸 보면 controller 클래스에서 매핑이 됨
		var data={
			key: key,
			content: "hello",
			name: "guest" //principle.getName 이름의 값으로 넣으면 될 듯..?
		}
		//접속하자마자 연결시도
		client.send("/bot/hello",{},JSON.stringify(data));
		//*/
	})
}

//웹소켓 종료
function disconnect() {
	client.disconnect(() => {
		console.log("Disconnected...")
	});
}

//종료(X) 버튼 클릭 시 이벤트
function btnCloseClicked() {
	$("#bot-container").hide();
	//대화창 리셋
	$("#chat-content").html("");
	disconnect();
	flag=false;

}

//챗봇 시작 시 버튼 이벤트
function btnBotClicked() {
	if(flag) return; //챗봇을 한 번만 켜주기 위해서
	//1. 소켓 접속
	$("#bot-container").show();
	connect()
	flag=true;
}

function clearQuestion(){
	$("#question").val("")//setter처럼 사용 (""없이 그냥이면 getter)
}

//메시지 전송
//사용자가 채팅메시지를 입력했을때
function btnMsgSendClicked(){
	var question = $("#question").val().trim(); //jQeury는 메서드(.으로 접근해서 객체 안에 있는걸 쓰는 것) 기반
	if(question.length<2){
		alert("질문은 최소 2글자 이상으로 부탁드립니다.");
		//clearQuestion();
		return;
	}
	var now = new Date();
	var time = formatTime(now);
	var tag = `
				<div class="msg user flex">
					
					<div class="message">
						<div class="part">
							<p>${question}</p>
						</div>
						<div class="time">${time}</div>
					</div>
				</div>
				`;
	
	showMessage(tag);
	
	
	var data={
			key: key,
			content: question,
			name: "guest" //principle.getName 이름의 값으로 넣으면 될 듯..?
		}
	
	client.send("/bot/question",{},JSON.stringify(data));
	clearQuestion()
	
}

$(function() {
	$("#btn-bot").click(btnBotClicked);
});