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
                location.href = "/view/board/" + data.result + "?page=" + page;
                // alert(data.result.boardId);
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
    let inputPassword = prompt('??????????????? ???????????????');
    inputPassword.trim();
    $.ajax({
        url: "/api/board/" + boardId,
        type: "DELETE",
        dataType: "json",
        data: JSON.stringify({"boardPassword" : inputPassword}),
        contentType: "application/json",
        success: function (data) {
            location.href="/view/board"
        },
        error: function (request, status, error) {
            let err = JSON.parse(request.responseText);
            alert(err.resultMessage)
            document.getElementById('boardPassword').focus();
        }
    });
}

function searchButton() {
    let langSelect = document.getElementById("search-type");
    let selectValue = langSelect.options[langSelect.selectedIndex].value;
    let searchValue = document.getElementById("search-value").value.trim();

    if (selectValue === 'createAt') {
        let startDate = document.getElementById("startDate").value.trim();
        let endDate = document.getElementById("endDate").value.trim();
        if (!startDate) {
            startDate = 'startDate';
        }
        if (!endDate) {
            endDate = 'endDate';
        }
        location.href="/view/board?searchType=" + selectValue + "&startDate=" + startDate + "&endDate=" + endDate + "&searchValue=" + searchValue;
    } else {
        location.href="/view/board?searchType=" + selectValue + "&searchValue=" + searchValue;
    }

}

function changeType(){
    let langSelect = document.getElementById("search-type");

    // select element?????? ????????? option??? value??? ????????????.
    let selectValue = langSelect.options[langSelect.selectedIndex].value;
    // select element?????? ????????? option??? text??? ????????????.
    let selectText = langSelect.options[langSelect.selectedIndex].text;

    if (selectValue === 'createAt') {

        let date1 = document.getElementById('date1');
        date1.innerHTML="<label id=\"label1\"for=\"startDate\" >?????? ?????? ??????</label>";

        let dateInput1 = document.createElement('input');
        dateInput1.setAttribute('id', 'startDate');
        dateInput1.setAttribute('type', 'date');
        dateInput1.setAttribute('name', 'startDate');
        dateInput1.setAttribute('value', 'null');
        date1.appendChild(dateInput1);

        let date2 = document.getElementById('date2');
        date2.innerHTML="<label id=\"label2\"for=\"endDate\" >?????? ????????? ??????</label>";

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
        alert("????????? ???????????????.");
        document.getElementById('boardTitle').focus();
        return false;
    }
    if (boardTitle.length > 100) {
        alert("????????? 100??? ????????? ???????????????.")
        document.getElementById('boardTitle').focus();
        return false;
    }
    if (!boardContent) {
        alert("????????? ????????? ???????????? 500??? ???????????? ?????????.")
        document.getElementById('boardContent').focus();
        return false;
    }
    if (boardContent.length >= 500) {
        alert("????????? 500 ????????? ???????????????.");
        document.getElementById('boardContent').focus();
        return false;
    }
    if (!boardUserName) {
        alert("???????????? ???????????????.");
        document.getElementById('boardUserName').focus();
        return false;
    }
    if (boardUserName.length > 10) {
        alert("???????????? 10??? ????????? ???????????????.");
        document.getElementById('boardUserName').focus();
        return false;
    }
    if (!boardPassword) {
        alert("??????????????? ???????????????.")
        document.getElementById('boardPassword').focus();
        return false;
    }
    let check = /^[0-9]+$/;
    if (boardPassword.length > 20 || !check.test(boardPassword)) {
        alert("??????????????? 20??? ??????, ????????? ???????????????.")
        return false;
    }
    return true;
}

function updateBoardValid(boardTitle, boardContent, boardPassword) {
    if (!boardTitle) {
        alert("????????? ???????????????.");
        document.getElementById('boardTitle').focus();
        return false;
    }
    if (boardTitle.length > 100) {
        alert("????????? 100??? ????????? ???????????????.")
        document.getElementById('boardTitle').focus();
        return false;
    }
    if (!boardContent) {
        alert("????????? ?????? ?????????.")
        document.getElementById('boardContent').focus();
        return false;
    }
    if (boardContent.length >= 500) {
        alert("????????? ????????? 500 ????????? ???????????????.");
        document.getElementById('boardContent').focus();
        return false;
    }
    if (!boardPassword) {
        alert("??????????????? ???????????????.")
        document.getElementById('boardPassword').focus();
        return false;
    }
    let check = /^[0-9]+$/;
    if (boardPassword.length > 20 || !check.test(boardPassword)) {
        alert("??????????????? 20??? ??????, ????????? ???????????????.")
        return false;
    }
    return true;
}