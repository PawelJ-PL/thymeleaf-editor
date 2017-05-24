function renderPage(content) {
    var newWindow = window.open('', 'Render template', 'toolbar=0, width=800, height=600');
    newWindow.document.body.innerHTML = content;
}
