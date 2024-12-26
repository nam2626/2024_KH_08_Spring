<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	window.onload = () => {
		document.querySelector('.true').onclick = (e) => {
			fetch("/map").then(response => response.json()).then((result) => {
				console.log(result);
				let tag = '';
				result.list.forEach((item) => {
					tag += `<p>\${item.memberId} / \${item.passwd} / \${item.name} / \${item.age}</p>`;
				})
				document.querySelector('#result').innerHTML = tag;
			});			
		}
	}

</script>
</head>
<body>
	<!-- /map을 ajax로 호출 #result에 결과 출력 -->
	<button type="button" class="true">정상 데이터 호출</button>
	<button type="button" class="false">Exception 호출</button>
	<hr>
	<div id="result"></div>
</body>
</html>



