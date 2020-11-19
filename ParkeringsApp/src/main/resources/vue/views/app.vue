<template id="app">
    <div class="wrapper">
        <header>
            <img src="https://www.flaticon.com/svg/static/icons/svg/1159/1159499.svg" alt="logo">
            <h3>Parkering<br> App</h3>
            <div class="logins">
                <button class="button-user-login">Logout</button>
                <button class="button- "></button>
            </div>
        </header>
        <aside>
            <div class="brukerInfo">
                <p class="userName">{{users.name}} {{users.lastName}}</p>
                <p class="userEmail"><i>{{users.mail}}</i></p>
            </div>

          <div id = userOperations>
            <a id="publishSpaceButton" :href=`${users.id}/publish-parkingspace`>Legg ut parkeringsplass</a>
            <a id="rentSpaceButton" :href=`${users.id}/parkingspaces`>Lei en parkeringsplass</a>
            <a id="deleteUserButton" :href=`/api/${users.id}/delete`>Slett meg som bruker</a>
            <a id="updateUserButton" :href=`${users.id}/user-update`>Oppdater brukerinfo.</a>
          </div>
        </aside>

        <main>

        </main>

    </div>
</template>
<script>
    Vue.component("app", {
        template: "#app",
        methods:({

        }),
        data:() =>({
            users:null,
        }),
        created() {
            const userId = this.$javalin.pathParams["userId"];
            fetch(`/api/users/${userId}`)
                .then(res => res.json())
                .then(res => {
                    this.users = res;
                }).catch(() => alert("Error getting user"));
        }
    });
</script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap');
    .brukerInfo{
        font-family: 'Roboto', sans-serif;
        width: 230px;
        height: 130px;
        background-color: #EDF2F7;
        border-radius: 10px;
        -webkit-box-shadow: 0px 0px 12px 0px rgba(0,0,0,0.30);
        -moz-box-shadow: 0px 0px 12px 0px rgba(0,0,0,0.30);
        box-shadow: 0px 0px 12px 0px rgba(0,0,0,0.30);
    }

    .brukerInfo p{
        margin-left: 12%;
    }
    .userName{
        font-size: 26px;

        padding-top: 17px;
        font-weight: 400;
    }
    .userEmail{
        font-size: 18px;
        font-weight: 600;
        color: #68D391;
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
    }
    header button:hover{
        cursor: pointer;
        transition: 0.5s ease-in-out;
        background-color: #b8e994;
    }
    .logins{
        margin-left: 84%;
    }
    .button-user-login{
        margin-left: 15px;
        background-color: #4CAF50;
    }

    aside{
        position: absolute;
        top:7%;
    }

    #publishSpaceButton {
      font-family: 'Roboto', sans-serif;
      padding: 12px 30px;
      color: white;
      text-decoration: none;
      border: none;
      text-align: center;
      display: inline-block;
      font-size: 16px;
      transition-duration: 0.3s;
        background-color: #68D391;
        border-radius: 7px;
    }

    #publishSpaceButton:hover {
      background-color: #4a69bd;
      box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);;
    }

    #rentSpaceButton {
      font-family: 'Roboto', sans-serif;
      padding: 12px 30px;
      color: white;
      text-decoration: none;
      border: none;
      text-align: center;
      display: inline-block;
      font-size: 16px;
      transition-duration: 0.3s;
        background-color: #68D391;
        border-radius: 7px;
    }

    #rentSpaceButton:hover {
      background-color: #4a69bd;
      box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);;
    }

    #deleteUserButton {
      font-family: 'Roboto', sans-serif;
      padding: 12px 30px;
      color: white;
      text-decoration: none;
      border: none;
      text-align: center;
      display: inline-block;
      font-size: 16px;
      transition-duration: 0.3s;
        background-color: #68D391;
        border-radius: 7px;
    }

    #deleteUserButton:hover {
      background-color: #4a69bd;
      box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);;
    }

    #updateUserButton {
      font-family: 'Roboto', sans-serif;
      padding: 12px 30px;
      color: white;
      text-decoration: none;
      border: none;
      text-align: center;
      display: inline-block;
      font-size: 16px;
      transition-duration: 0.3s;
        background-color: #68D391;
        border-radius: 7px;
    }

    #updateUserButton:hover {
      background-color: #4a69bd;
      box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);;
    }

    .brukerInfo {
      position: relative;
      top: 100px;
    }

    #userOperations {
      position: relative;
      top: 160px;
    }

</style>
