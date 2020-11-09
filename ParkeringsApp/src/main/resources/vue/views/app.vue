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
                <p>Bruker info:</p>
                <p>{{users.name}} {{users.lastName}}</p>
                <p>{{users.mail}}</p>
            </div>
            <a id="publishSpaceButton" :href=`${users.id}/publish-parkingspace`>Legg ut parkeringsplass</a>
            <a id="rentSpaceButton" :href=`${users.id}/parkingspaces`>Lei en parkeringsplass</a>
            <a id="deleteUserButton" :href=`/api/${users.id}/delete`>Delete user</a>
            <a id="updateUserButton" :href=`${users.id}/user-update`>Update user</a>

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
      background-color: #4CAF50;
      color: white;
      text-decoration: none;
      border: none;
      text-align: center;
      display: inline-block;
      font-size: 16px;
      transition-duration: 0.3s;
    }

    #publishSpaceButton:hover {
      background-color: #4a69bd;
      box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);;
    }

    #rentSpaceButton {
      font-family: 'Roboto', sans-serif;
      padding: 12px 30px;
      background-color: #4CAF50;
      color: white;
      text-decoration: none;
      border: none;
      text-align: center;
      display: inline-block;
      font-size: 16px;
      transition-duration: 0.3s;
    }

    #deleteUserButton {
      font-family: 'Roboto', sans-serif;
      padding: 12px 30px;
      background-color: #4CAF50;
      color: white;
      text-decoration: none;
      border: none;
      text-align: center;
      display: inline-block;
      font-size: 16px;
      transition-duration: 0.3s;
    }

    #updateUserButton {
      font-family: 'Roboto', sans-serif;
      padding: 12px 30px;
      background-color: #4CAF50;
      color: white;
      text-decoration: none;
      border: none;
      text-align: center;
      display: inline-block;
      font-size: 16px;
      transition-duration: 0.3s;
    }

    #rentSpaceButton:hover {
      background-color: #4a69bd;
      box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);;
    }

</style>
