//게시글 등록
function postArticle(boardTitle, boardContent) {

    const data = {
        category: $('#urlCategory').val(),
        categoryBoard: $('#categoryBoard').val(),
        boardTitle: boardTitle,
        boardContent: boardContent
    }

    $.ajax({
        type: 'POST',
        url: '/' + data.category + '/write',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function () {
            alert('게시글 등록')
            window.location.href = '/' + data.category
        },
        error: function (data) {
            console.log(data)
        },
    })
}

//게시글 validation
function validateArticleForm() {
    let form = $("#articleForm")[0];
    let boardTitle = form.boardTitle.value.trim();
    let boardContent = form.boardContent.value.trim();
    if (boardTitle.length < 3 || boardTitle.length > 100) {
        alert('제목은 3자 이상 100자 이하입니다.')
        form.boardTitle.focus();
        return false;
    }
    if (boardContent.length < 3 || boardContent.length > 2000) {
        alert('내용은 3자 이상 2000자 이하입니다.')
        form.boardContent.focus();
        return false;
    }
    postArticle(boardTitle, boardContent);
}

function validatedAttachedForm() {

}
