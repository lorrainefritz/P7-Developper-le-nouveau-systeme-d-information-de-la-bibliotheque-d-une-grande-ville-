
function test () {
	window.alert("test");
}
//_______________________Validation d'un livre____________________________________

function validateBook(){
	document.getElementById("errorTitleBook").innerHTML = "";
	document.getElementById("errorAuthorBook").innerHTML = "";
	document.getElementById("errorLanguageBook").innerHTML = "";
	document.getElementById("errorTypeBook").innerHTML = "";
	document.getElementById("errorSummaryBook").innerHTML = "";
	document.getElementById("errorPublisherBook").innerHTML = "";
	document.getElementById("errorCreationDateBook").innerHTML = "";
	document.getElementById("errorShelveBook").innerHTML = "";
	emptyTitleBook();
	emptyAuthorBook();
	emptyLanguageBook();
	emptyTypeBook();
	emptySummaryBook();
	emptyPublisherBook();
	emptyCreationDateBook();
	emptyShelveBook();
	titleBookLenghtIsNotValid();
	authorBookLenghtIsNotValid();
	languageBookLenghtIsNotValid();
	typeBookLenghtIsNotValid();
	summaryBookLenghtIsNotValid();
	publisherBookLenghtIsNotValid();
	shelveBookLenghtIsNotValid();
}

function emptyTitleBook() {
	var titleBook = document.getElementById("titleBook").value;
	if (titleBook == "") {
		document.getElementById("errorTitleBook").innerHTML = "Le champs titre est vide";
// 		event.preventDefault();
	}	
}

function emptyAuthorBook() {
	var authorBook = document.getElementById("authorBook").value;
	if (authorBook == "") {
		document.getElementById("errorAuthorBook").innerHTML = "Le champs auteur est vide";
// 		event.preventDefault();
	}	
}

function emptyLanguageBook() {
	var languageBook = document.getElementById("languageBook").value;
	if (languageBook == "") {
		document.getElementById("errorTitleBook").innerHTML = "Le champs langue est vide";
// 		event.preventDefault();
	}	
}

function emptyTypeBook() {
	var typeBook = document.getElementById("typeBook").value;
	if (typeBook == "") {
		document.getElementById("errorTypeBook").innerHTML = "Le champs genre est vide";
// 		event.preventDefault();
	}	
}
function emptySummaryBook() {
	var summaryBook = document.getElementById("summaryBook").value;
	if (summaryBook == "") {
		document.getElementById("errorSummaryBook").innerHTML = "Le champs résumé est vide";
// 		event.preventDefault();
	}	
}
function emptyPublisherBook() {
	var publisherBook = document.getElementById("publisherBook").value;
	if (publisherBook == "") {
		document.getElementById("errorPublisherBook").innerHTML = "Le champs éditeur est vide";
// 		event.preventDefault();
	}	
}
function emptyCreationDateBook() {
	var creationDateBook = document.getElementById("creationDateBook").value;
	if (creationDateBook == "") {
		document.getElementById("errorCreationDateBook").innerHTML = "Le champs date est vide";
// 		event.preventDefault();
	}	
}
function emptyShelveBook() {
	var shelveBook = document.getElementById("shelveBook").value;
	if (shelveBook == "") {
		document.getElementById("errorShelveBook").innerHTML = "Le champs référencement est vide";
// 		event.preventDefault();
	}	
}



function titleBookLenghtIsNotValid() {
	var titleBook = document.getElementById("titleBook").value;
	if (titleBook < 0 || titleBook.length > 65) {		
		document.getElementById("errorTitleBook").innerHTML = "La valeur du champs titre doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}

function authorBookLenghtIsNotValid() {
	var authorBook = document.getElementById("authorBook").value;
	if (authorBook < 0 || authorBook.length > 65) {		
		document.getElementById("errorAuthorBook").innerHTML = "La valeur du champs auteur doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}

function languageBookLenghtIsNotValid() {
	var languageBook = document.getElementById("languageBook").value;
	if (languageBook < 0 || languageBook.length > 65) {		
		document.getElementById("errorLanguageBook").innerHTML = "La valeur du champs langage doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}
function typeBookLenghtIsNotValid() {
	var typeBook = document.getElementById("typeBook").value;
	if (typeBook < 0 || typeBook.length > 65) {		
		document.getElementById("errorTypeBook").innerHTML = "La valeur du champs genre doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}
function summaryBookLenghtIsNotValid() {
	var summaryBook = document.getElementById("summaryBook").value;
	if (summaryBook < 0 || summaryBook.length > 65) {		
		document.getElementById("errorSummaryBook").innerHTML = "La valeur du champs résumé doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}
function publisherBookLenghtIsNotValid() {
	var publisherBook = document.getElementById("publisherBook").value;
	if (publisherBook < 0 || publisherBook.length > 65) {		
		document.getElementById("errorPublisherBook").innerHTML = "La valeur du champs éditeur doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}

function shelveBookLenghtIsNotValid() {
	var shelveBook = document.getElementById("shelveBook").value;
	if (shelveBook < 0 || shelveBook.length > 65) {		
		document.getElementById("errorShelveBook").innerHTML = "La valeur du champs référencement doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}




//_______________________Validation d'une image ____________________________________

function validateImage(){
	document.getElementById("errorImageBook").innerHTML = "";
	imageInvalidFormat()
	emptyImage();
	
	
}

function emptyImageBook() {
	var image = document.getElementById("imageBook").value;
	if (image == "") {
		document.getElementById("errorImageBook").innerHTML = "Le champs image est vide";
//		event.preventDefault();
	}
}



function imageInvalidFormat() {
	var image = document.getElementById("imageBook").value;
	var png = "^[a-zA-Z0-9]+\.png$";
	var jpeg = "^[a-zA-Z0-9]+\.jpeg$";
	if (!image.match(png)&&!image.match(jpeg)) {
		document.getElementById("errorImageBook").innerHTML = "Merci de rentrer un nom d'image en .jpeg ou .png";
//		event.preventDefault();
	}
}



//_______________________Validation ajout D'une bibliothèque____________________________________

function validateLibrary(){
	document.getElementById("errorNameLibrary").innerHTML = "";
	document.getElementById("errorAddressLibrary").innerHTML = "";
	document.getElementById("errorEmailLibrary").innerHTML = "";
	document.getElementById("errorPhoneLibrary").innerHTML = "";
	document.getElementById("errorOpeningTimeLibrary").innerHTML = "";
	emptyNameLibrary(); 
	emptyAddressLibrary();
	emptyEmailLibrary(); 
	emptyPhoneLibrary();
	emptyOpeningTimeLibrary();
	nameLibraryLenghtIsNotValid();
	addressLibraryLenghtIsNotValid();
	emailLibraryLenghtIsNotValid();
	phoneLibraryLenghtIsNotValid();
	openingTimeLibraryLenghtIsNotValid();
	
}

function emptyNameLibrary(){
	var nameLibrary = document.getElementById("nameLibrary").value;
	if (nameLibrary == "") {
		document.getElementById("errorNameLibrary").innerHTML = "Le champs nom est vide";
// 		event.preventDefault();
	}	
}

function emptyAddressLibrary(){
	var addressLibrary = document.getElementById("addressLibrary").value;
	if (addressLibrary == "") {
		document.getElementById("errorAddressLibrary").innerHTML = "Le champs adresse est vide";
// 		event.preventDefault();
	}	
}

function emptyEmailLibrary(){
	var emailLibrary = document.getElementById("emailLibrary").value;
	if (emailLibrary == "") {
		document.getElementById("errorEmailLibrary").innerHTML = "Le champs email est vide";
// 		event.preventDefault();
	}	
}
function emptyPhoneLibrary(){
	var phoneLibrary = document.getElementById("phoneLibrary").value;
	if (phoneLibrary == "") {
		document.getElementById("errorPhoneLibrary").innerHTML = "Le champs téléphone est vide";
// 		event.preventDefault();
	}	
}

function emptyOpeningTimeLibrary(){
	var openingTimeLibrary = document.getElementById("openingTimeLibrary").value;
	if (openingTimeLibrary == "") {
		document.getElementById("errorOpeningTimeLibrary").innerHTML = "Le champs horaires d'ouverture est vide";
// 		event.preventDefault();
	}	
}


function nameLibraryLenghtIsNotValid() {
	var nameLibrary = document.getElementById("nameLibrary").value;
	if (nameLibrary < 0 || nameLibrary.length > 65) {		
		document.getElementById("errorNameLibrary").innerHTML = "La valeur du champs nom doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}

function addressLibraryLenghtIsNotValid() {
	var addressLibrary = document.getElementById("addressLibrary").value;
	if (addressLibrary < 0 || addressLibrary.length > 65) {		
		document.getElementById("errorAddressLibrary").innerHTML = "La valeur du champs adresse doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}

function emailLibraryLenghtIsNotValid() {
	var emailLibrary = document.getElementById("emailLibrary").value;
	if (emailLibrary < 0 || emailLibrary.length > 65) {		
		document.getElementById("errorEmailLibrary").innerHTML = "La valeur du champs email doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}

function phoneLibraryLenghtIsNotValid() {
	var phoneLibrary = document.getElementById("phoneLibrary").value;
	if (phoneLibrary < 0 || phoneLibrary.length > 65) {		
		document.getElementById("errorPhoneLibrary").innerHTML = "La valeur du champs téléphone doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}

function openingTimeLibraryLenghtIsNotValid() {
	var openingTimeLibrary = document.getElementById("openingTimeLibrary").value;
	if (openingTimeLibrary < 0 || openingTimeLibrary.length > 65) {		
		document.getElementById("errorOpeningTimeLibrary").innerHTML = "La valeur du champs Horaires d'ouverture doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}




//_______________________Validation ajout d'un utilisateur =>registration____________________________________

function validateRegistration(){
	document.getElementById("errorFirstNameRegistration").innerHTML = "";
	document.getElementById("errorLastNameRegistration").innerHTML = "";
	document.getElementById("errorEmailRegistration").innerHTML = "";
	document.getElementById("errorAddressRegistration").innerHTML = "";
	document.getElementById("errorPhoneRegistration").innerHTML = "";
	document.getElementById("errorPasswordRegistration").innerHTML = "";
	
	emptyFirstNameRegistration();
	emptyLastNameRegistration();
	emptyEmailRegistration();
	emptyAddressRegistration();
	emptyPhoneRegistration();
	emptyPasswordRegistration();
	
	firstNameRegistrationLenghtIsNotValid();
	lastNameRegistrationLenghtIsNotValid();
	emailRegistrationLenghtIsNotValid();
	addressRegistrationLenghtIsNotValid();
	phoneRegistrationLenghtIsNotValid();
	passwordRegistrationLenghtIsNotValid();
	
}

function emptyFirstNameRegistration() {
	var firstNameRegistration = document.getElementById("firstNameRegistration").value;
	if (firstNameRegistration == "") {
		document.getElementById("errorFirstNameRegistration").innerHTML = "Le champs prénom est vide";
// 		event.preventDefault();
	}	
}
function emptyLastNameRegistration() {
	var lastNameRegistration = document.getElementById("lastNameRegistration").value;
	if (lastNameRegistration == "") {
		document.getElementById("errorLastNameRegistration").innerHTML = "Le champs nom est vide";
// 		event.preventDefault();
	}	
}

function emptyEmailRegistration() {
	var emailRegistration = document.getElementById("emailRegistration").value;
	if (emailRegistration == "") {
		document.getElementById("errorEmailRegistration").innerHTML = "Le champs email est vide";
// 		event.preventDefault();
	}	
}

function emptyAddressRegistration() {
	var addressRegistration = document.getElementById("addressRegistration").value;
	if (addressRegistration == "") {
		document.getElementById("errorAddressRegistration").innerHTML = "Le champs adresse est vide";
// 		event.preventDefault();
	}	
}

function emptyPhoneRegistration() {
	var phoneRegistration = document.getElementById("phoneRegistration").value;
	if (phoneRegistration == "") {
		document.getElementById("errorPhoneRegistration").innerHTML = "Le champs telephone est vide";
// 		event.preventDefault();
	}	
}


function emptyPasswordRegistration() {
	var passwordRegistration = document.getElementById("passwordRegistration").value;
	if (passwordRegistration == "") {
		document.getElementById("errorPasswordRegistration").innerHTML = "Le champs mot de passe est vide";
// 		event.preventDefault();
	}	
}


function firstNameRegistrationLenghtIsNotValid() {
	var firstNameRegistration = document.getElementById("firstNameRegistration").value;
	if (firstNameRegistration < 0 || firstNameRegistration.length > 65) {		
		document.getElementById("errorFirstNameRegistration").innerHTML = "La valeur du champs prénom doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}

function lastNameRegistrationLenghtIsNotValid() {
	var lastNameRegistration = document.getElementById("lastNameRegistration").value;
	if (lastNameRegistration < 0 || lastNameRegistration.length > 65) {		
		document.getElementById("errorLastNameRegistration").innerHTML = "La valeur du champs nom doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}
function emailRegistrationLenghtIsNotValid() {
	var emailRegistration = document.getElementById("emailRegistration").value;
	if (emailRegistration < 0 || emailRegistration.length > 65) {		
		document.getElementById("errorEmailRegistration").innerHTML = "La valeur du champs email doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}
function addressRegistrationLenghtIsNotValid() {
	var addressRegistration = document.getElementById("addressRegistration").value;
	if (addressRegistration < 0 || addressRegistration.length > 65) {		
		document.getElementById("errorAddressRegistration").innerHTML = "La valeur du champs adresse doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}


function phoneRegistrationLenghtIsNotValid() {
	var phoneRegistration = document.getElementById("phoneRegistration").value;
	if (phoneRegistration < 0 || phoneRegistration.length > 65) {		
		document.getElementById("errorPhoneRegistration").innerHTML = "La valeur du champs téléphone doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}

function passwordRegistrationLenghtIsNotValid() {
	var passwordRegistration = document.getElementById("passwordRegistration").value;
	if (passwordRegistration < 0 || passwordRegistration.length > 65) {		
		document.getElementById("errorPasswordRegistration").innerHTML = "La valeur du champs mot de passe doit être compris entre 1 et 65 caractères";
//		event.preventDefault();
	}
}