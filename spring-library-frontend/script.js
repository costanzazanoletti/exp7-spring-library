/* COSTANTI*/
const apiUrl = 'http://localhost:8080/api/v1/books';
const root = document.getElementById('root');

/* FUNZIONI */
// funzione che renderizza le categorie del book
const renderCategories = (categories) => {
  console.log(categories);
  let content;
  if (categories.length === 0) {
    content = 'No categories';
  } else {
    content = '<ul class="list-unstyled">';
    categories.forEach((cat) => {
      content += `<li>${cat.name}</li>`;
    });
    content += '</ul>';
  }
  return content;
};

// funzione che renderizza la card del book
const renderBook = (element) => {
  console.log(element);
  return `<div class="card shadow h-100">
    <div class="card-body">
        <h5 class="card-title">${element.title}</h5>
        <h6 class="card-subtitle mb-2 text-body-secondary">${
          element.authors
        }</h6>
        <p class="card-text">${
          element.synopsis != '' ? element.synopsis : 'N.D.'
        }</p>
        <p>${element.publisher} ${element.year}</p>
        <p>ISBN: ${element.isbn}</p>
        <p>N.copies: ${element.numberOfCopies} (${
    element.availableCopies
  } available)</p>
    </div>
    <div class="card-footer">${renderCategories(element.categories)}</div>
  </div>`;
};

// funzione che renderizza la gallery di card
const renderBookList = (data) => {
  let content;
  console.log(data);
  if (data.length > 0) {
    // creo la gallery
    content = '<div class="row">';
    // itero sull'array di libri
    data.forEach((element) => {
      content += '<div class="col-3">';
      // chiamo il metodo che restituisce la card del book
      content += renderBook(element);
      content += '</div>';
    });
    content += '</div>';
  } else {
    content = '<div class="alert alert-info">The list is empty</div>';
  }
  // sostituisco il contenuto di root con il mio content
  root.innerHTML = content;
};

// funzione che chiama l'api e ottiene il json con l'array di books
const getBooks = async () => {
  try {
    // ottengo la response dell'api
    const response = await axios.get(apiUrl);
    // passo i dati alla funzione che li renderizza
    renderBookList(response.data);
  } catch (error) {
    console.log(error);
  }
};

/* codice globale che viene eseguito al load dello script */
getBooks();
