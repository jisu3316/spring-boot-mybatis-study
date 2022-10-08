function createBoard(page) {
    let boardTitle = document.getElementById('boardTitle').value.trim();
    let boardContent = document.getElementById('boardContent').value.trim();
    let boardUserName = document.getElementById('boardUserName').value.trim();
    let boardPassword = document.getElementById('boardPassword').value.trim();
    if (createBoardValid(boardTitle, boardContent, boardUserName, boardPassword)) {
        $.ajax({
            url: "/api/board",
            type: "POST",
            dataType: "json",
            data: JSON.stringify({"boardTitle" : boardTitle, "boardContent" : boardContent, "boardUserName" : boardUserName, "boardPassword" : boardPassword}),
            contentType: "application/json",
            success: function (data) {
                console.log("data: ", data);
                location.href = "/view/board/" + data.result + "?page=" + page;

            },
            error: function (request, status, error) {
                let err = JSON.parse(request.responseText);
                alert(err.resultCode)
            }
        });
    }
}


function updateBoard(page) {
    let boardId = document.getElementById('boardId').value;
    let boardTitle = document.getElementById('boardTitle').value.trim();
    let boardContent = document.getElementById('boardContent').value.trim();
    let boardUserName = document.getElementById('boardUserName').value.trim();
    let boardPassword = document.getElementById('boardPassword').value.trim();
    if (updateBoardValid(boardTitle, boardContent, boardPassword)) {
        $.ajax({
            url: "/api/board/" + boardId,
            type: "PUT",
            dataType: "json",
            data: JSON.stringify({"boardId": boardId, "boardTitle" : boardTitle, "boardContent" : boardContent, "boardUserName" : boardUserName, "boardPassword" : boardPassword}),
            contentType: "application/json",
            success: function (data) {
                console.log("data: ", data);
                location.href = "/view/board/" + data.result + "?page=" + page;
                // alert(data.result.boardId);
                // console.log(data);
            },
            error: function (request, status, error) {
                let err = JSON.parse(request.responseText);
                alert(err.resultMessage);
                document.getElementById('boardPassword').focus();
            }
        });
    }
}


function deleteBoard(boardId) {
    let inputPassword = prompt('비밀번호를 입력하세요');
    inputPassword.trim();
    console.log(boardId);
    $.ajax({
        url: "/api/board/" + boardId,
        type: "DELETE",
        dataType: "json",
        data: JSON.stringify({"boardPassword" : inputPassword}),
        contentType: "application/json",
        success: function (data) {
            console.log("data: ", data);
            location.href="/view/board"
        },
        error: function (request, status, error) {
            let err = JSON.parse(request.responseText);
            console.log(err);
            alert(err.resultMessage)
            document.getElementById('boardPassword').focus();
        }
    });
}

function searchButton() {
    let langSelect = document.getElementById("search-type");
    let selectValue = langSelect.options[langSelect.selectedIndex].value;
    let searchValue = document.getElementById("search-value").value.trim();
    console.log("selectValue= ",selectValue, ", searchValue= ", searchValue, )

    if (selectValue === 'createAt') {
        let startDate = document.getElementById("startDate").value.trim();
        let endDate = document.getElementById("endDate").value.trim();
        if (!startDate) {
            startDate = 'startDate';
            console.log("startDate null일때 selectValue= ", selectValue, ", searchValue= ", searchValue, ", startDate= ", startDate, ", endDate= ", endDate);
        }
        if (!endDate) {
            endDate = 'endDate';
            console.log("endDate null일때 selectValue= ", selectValue, ", searchValue= ", searchValue, ", startDate= ", startDate, ", endDate= ", endDate);
        }
        location.href="/view/board?searchType=" + selectValue + "&startDate=" + startDate + "&endDate=" + endDate + "&searchValue=" + searchValue;
    } else {
        location.href="/view/board?searchType=" + selectValue + "&searchValue=" + searchValue;
    }

}

function changeType(){
    let langSelect = document.getElementById("search-type");

    // select element에서 선택된 option의 value가 저장된다.
    let selectValue = langSelect.options[langSelect.selectedIndex].value;
    console.log(selectValue);
    // select element에서 선택된 option의 text가 저장된다.
    let selectText = langSelect.options[langSelect.selectedIndex].text;
    console.log(selectText);

    if (selectValue === 'createAt') {
        console.log("createAt 선택");

        let date1 = document.getElementById('date1');
        date1.innerHTML="<label id=\"label1\"for=\"startDate\" >검색 시작 날짜</label>";

        let dateInput1 = document.createElement('input');
        dateInput1.setAttribute('id', 'startDate');
        dateInput1.setAttribute('type', 'date');
        dateInput1.setAttribute('name', 'startDate');
        dateInput1.setAttribute('value', 'null');
        date1.appendChild(dateInput1);

        let date2 = document.getElementById('date2');
        date2.innerHTML="<label id=\"label2\"for=\"endDate\" >검색 끝나는 날짜</label>";

        let dateInput2 = document.createElement('input');
        dateInput2.setAttribute('id', 'endDate');
        dateInput2.setAttribute('type', 'date');
        dateInput2.setAttribute('name', 'endDate');
        dateInput2.setAttribute('value', 'null');
        date2.appendChild(dateInput2);

    } else {
        let startDate = document.getElementById('startDate');
        let endDate = document.getElementById('endDate');
        if (startDate != null || endDate != null) {
            document.getElementById('label1').remove();
            document.getElementById('label2').remove();
            document.getElementById('startDate').remove();
            document.getElementById('endDate').remove();
        }

    }
}

function createBoardValid(boardTitle, boardContent, boardUserName, boardPassword) {
    if (!boardTitle) {
        alert("제목을 채워주세요.");
        document.getElementById('boardTitle').focus();
        return false;
    }
    if (boardTitle.length > 100) {
        alert("제목은 100자 이하만 가능합니다.")
        document.getElementById('boardTitle').focus();
        return false;
    }
    if (!boardContent) {
        alert("내용은 빈칸이 아니거라 500자 이하여야 합니다.")
        document.getElementById('boardContent').focus();
        return false;
    }
    if (boardContent.length >= 500) {
        alert("내용은 500 이하만 가능합니다.");
        document.getElementById('boardContent').focus();
        return false;
    }
    if (!boardUserName) {
        alert("작성자를 채워주세요.");
        document.getElementById('boardUserName').focus();
        return false;
    }
    if (boardUserName.length > 10) {
        alert("작성자는 10자 이하만 가능합니다.");
        document.getElementById('boardUserName').focus();
        return false;
    }
    if (!boardPassword) {
        alert("비밀번호를 채워주세요.")
        document.getElementById('boardPassword').focus();
        return false;
    }
    let check = /^[0-9]+$/;
    if (boardPassword.length > 20 || !check.test(boardPassword)) {
        alert("비밀번호는 20자 이하, 숫자만 가능합니다.")
        return false;
    }
    return true;
}

function updateBoardValid(boardTitle, boardContent, boardPassword) {
    if (!boardTitle) {
        alert("제목을 채워주세요.");
        document.getElementById('boardTitle').focus();
        return false;
    }
    if (boardTitle.length > 100) {
        alert("제목은 100자 이하만 가능합니다.")
        document.getElementById('boardTitle').focus();
        return false;
    }
    if (!boardContent) {
        alert("내용을 채워 주세요.")
        document.getElementById('boardContent').focus();
        return false;
    }
    if (boardContent.length >= 500) {
        alert("게시글 내용은 500 이하만 가능합니다.");
        document.getElementById('boardContent').focus();
        return false;
    }
    if (!boardPassword) {
        alert("비밀번호를 채워주세요.")
        document.getElementById('boardPassword').focus();
        return false;
    }
    let check = /^[0-9]+$/;
    if (boardPassword.length > 20 || !check.test(boardPassword)) {
        alert("비밀번호는 20자 이하, 숫자만 가능합니다.")
        return false;
    }
    return true;
}