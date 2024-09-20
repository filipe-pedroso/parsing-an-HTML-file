document.addEventListener("DOMContentLoaded", function() {
    let heading = document.createElement("h1");
    heading.textContent = "Bem-vindo ao exemplo de HTML!";
    document.body.appendChild(heading);

    let paragraph = document.createElement("p");
    paragraph.textContent = "Esse é um exemplo simples de JavaScript interagindo com HTML.";
    document.body.appendChild(paragraph);
    
    console.log("Página carregada e scripts executados.");
});
