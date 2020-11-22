<template id="app-login">
    <div class="wrapper">
        <header>
            <img src="https://www.flaticon.com/svg/static/icons/svg/1159/1159499.svg" alt="logo">
            <h3>Parkering<br> App</h3>
            <div class="btn-group">
                <button>Admin innlogging</button>
                <button v-on:click="modalActive = true">Logg inn</button>
            </div>
        </header>
        <br> <br>

        <div class="modalFormWrap" v-bind:style='{display:(modalActive?"block":"none")}'>
            <div v-bind:style='{height:(register?"500px":"320px")}' class="modalForm">
                <div class="modalHeader">
                    <h3>Logg inn</h3>
                    <span v-on:click="modalActive = false, register=false">&#10799;</span>
                </div>
                <form v-bind:action='(register?"/api/register":"/api/login")' method="post">
                    <div v-bind:style='{display:(register?"block":"none")}' class="reg">
                        <p>Fornavn</p>
                        <input name="name" type="text" placeholder="Name">
                        <p>Etternavn</p>
                        <input name="lastName" type="text" placeholder="Lastname">
                    </div>
                    <p>Epost</p>
                    <input name="email" type="text" placeholder="Email">
                    <p>Passord</p>
                    <input name="password" type="password" placeholder="Password">
                    <p v-on:click="register=true" class="userRegister">Registrer deg</p>
                    <button class="modalRegister" v-bind:style='{display: (register?"block":"none")}'>Registrer deg</button>
                    <button class="modalLogin" v-bind:style='{display:(register?"none":"block")}'>Logg inn</button>
                </form>
            </div>
        </div>

        <main>
            <div class="parkingSpaces">
                <ul class="parkinSpacesList">
                    <li v-for="space in spaces">

                        <div class="singleSpace">
                            <h2>Parking Space {{space.spaceId}}</h2>
                            <div class="infoSpace">
                                <div class="address">
                                    <h5>By: {{space.city}}</h5>
                                    <h5 class="adressName">{{space.address}}</h5>
                                </div>
                                <div class="spaceInfo">
                                    <h5>Størrelse: {{space.size_sqm}}</h5>
                                    <h5 class="spaceSq">Pris: {{space.price_ph}}</h5>
                                </div>
                            </div>
                            <div class="userInfo">
                                <div class="userNames">
                                    <p class="madeBy">Laget av:</p>
                                    <p class="name">{{space.byUser.name}}</p>
                                    <p>{{space.byUser.lastName}}</p>
                                </div>
                                <p>{{space.byUser.mail}}</p>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>

        </main>

    </div>
</template>
<script>
    Vue.component("app-login", {
        template: "#app-login",
        methods:({
            openModal: function () {
            }
        }),
        data:() =>({
            modalActive:false,
            register:false,
            users:[],
            spaces:[]
        }),
        created() {
            fetch("/api/users" && "/api/parking-spaces")
                .then(res => res.json())
                .then(res => {
                    this.users = res;
                    this.spaces = res;
                }).catch(() => alert("Error getting user"));
        }
    });
</script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap');

    .parkingSpaces{
        display: flex;
        flex-direction: row;
        font-family: 'Roboto', sans-serif;
        font-weight: 300;
        color: #1A202C;
        height: auto;
        width: 350px;
        border-radius: 10px;
        background-color: #EDF2F7;
        -webkit-box-shadow: 0px 0px 12px 0px rgba(0,0,0,0.25);
        -moz-box-shadow: 0px 0px 12px 0px rgba(0,0,0,0.25);
        box-shadow: 0px 0px 12px 0px rgba(0,0,0,0.25);
    }
    .parkingSpaces h2{
        margin-bottom: 15px;
        margin-right: 30px;
    }
    .address{
        border: 1px solid black;
        height: 60px;
        width: 110px;
        border-radius: 10px;
        align-content: center;
        margin-right: 6px;
    }
    .address h5{
        margin-top: 9px;
        margin-left: 15px;
        margin-bottom: 8px;
    }
    .adressName{
        margin-top: -10px;
    }

    .spaceInfo{
        border: 1px solid black;
        height: 60px;
        width: 110px;
        border-radius: 10px;
        align-content: center;
    }
    .spaceInfo h5{
        margin-top: 9px;
        margin-left: 15px;
        margin-bottom: 8px;
    }
    .spaceSq{
        margin-top: -10px;
    }
    .infoSpace{
        display: flex;
        flex-direction: row;
    }
    .userNames{
        display: flex;
        flex-direction: row;
        margin-bottom: -15px;
    }
    .madeBy{
        margin-right: 5px;
    }
    .name{
        margin-right: 5px;
    }
    .singleSpace{
        margin-right: 20px;
    }


    header img{
        width: 40px;
        margin: 30px;
    }
    header{
        display: flex;
        flex-direction: row;
        align-items: center;
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 9999;
        -webkit-box-shadow: 0px 10px 36px -15px rgba(0,0,0,0.50);
        -moz-box-shadow: 0px 10px 36px -15px rgba(0,0,0,0.50);
        box-shadow: 0px 10px 36px -15px rgba(0,0,0,0.50);
    }
    header h3{
        font-family: 'Roboto', sans-serif;
        font-weight: 300;
    }
    header button{
        font-family: 'Roboto', sans-serif;
        font-size: 17px;
        font-weight: 400;
        color: #222f3e;
        height: 30px;
        border-radius: 5px;
        border-style: none;
        margin: 10px;
        margin-right : 20px;
    }
    header button:hover{
        cursor: pointer;
        transition: 0.5s ease-in-out;
        background-color: #b8e994;
    }
    .logins{
        margin-left: 50px;
    }

    .button-user-login{
        margin-left: 15px;
        background-color: #1dd1a1;
    }
    .button-admin-login{
        background-color: #54a0ff;
        margin : 10px;
    }

    .modalFormWrap{
        z-index: 1;
        position: fixed;
        width: 100%;
        height: 100%;
        backdrop-filter: blur(4px);
        -webkit-backdrop-filter: blur(4px);
        display: none;
    }
    .modalForm{
        z-index: 9;
        position: relative;
        top: 5%;
        left: 40%;
        background-color: white;
        width: 340px;
        height: 320px;
        border-radius: 10px;
        -webkit-box-shadow: 0px 0px 49px -24px rgba(0,0,0,0.60);
        -moz-box-shadow: 0px 0px 49px -24px rgba(0,0,0,0.60);
        box-shadow: 0px 0px 49px -24px rgba(0,0,0,0.60);
    }

    .modalForm form p{
        font-family: Roboto, sans-serif;
        display: flex;
        margin-bottom: 0.5em;
        margin-left: 1.56em;
        font-size: 13px;
    }
    .modalForm form input{
        height: 35px;
        width: 300px;
        border-radius: 6px;
        border: solid 1px #B6B6B6;
        margin-left: 1.25em;
        margin-bottom: 1.2em;
        font-family: Roboto, sans-serif;
    }
    .modalForm form button{
        background-color: #78e08f;
        border: none;
        border-radius: 4px;
        width: 20em;
        height: 35px;
        font-family: Roboto, sans-serif;
        font-weight: 600;
        font-size: 15px;
        margin-left: 6%;
        margin-top: 20px;
    }
    .modalForm form button:hover{
        cursor: pointer;
        transition: 0.5s ease-in-out;
        background-color:#b8e994;
    }
    .modalHeader{
        display: flex;
        flex-direction: row;
        align-items: center;
        border-bottom: 1px solid #B6B6B6;
    }
    .modalHeader h3{
        margin-left: 10px;
        font-family: Roboto, sans-serif;
        font-weight: 500;
        font-size: 15px;
        color: #555555;
    }
    .modalHeader span{
        margin-left: 222px;
    }
    .modalHeader span:hover{
        cursor: pointer;
    }
    .userRegister{
        color:#4a69bd ;
    }
    .userRegister:hover{
        cursor: pointer;
    }

    .btn-group{
        margin-left: 82%;
    }
    .btn-group button {
        background-color: #68D391; /* Green background */

        color: white; /* White text */
        padding: 10px 24px; /* Some padding */
        cursor: pointer; /* Pointer/hand icon */
        float: left; /* Float the buttons side by side */
        margin:10px;
        height: 45px;
    }

    .btn-group button:not(:last-child) {
        border-right: none; /* Prevent double borders */
    }

    /* Clear floats (clearfix hack) */
    .btn-group:after {
        content: "";
        clear: both;
        display: table;
    }

    /* Add a background color on hover */
    .btn-group button:hover {
        background-color: #3e8e41;
    }

</style>
