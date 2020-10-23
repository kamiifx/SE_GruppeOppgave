<template id="app-login">
    <div class="wrapper">
        <header>
            <img src="https://www.flaticon.com/svg/static/icons/svg/1159/1159499.svg" alt="logo">
            <h3>Parkering<br> App</h3>
            <div class="logins">
                <button class="button-admin-login">Admin Login</button>
                <button class="button-user-login" v-on:click="modalActive = true">Login</button>
            </div>
        </header>
        <div class="modalFormWrap" v-bind:style='{display:(modalActive?"block":"none")}'>
            <div class="modalForm">
                <div class="modalHeader">
                    <h3>User Login</h3>
                    <span v-on:click="modalActive = false">&#10799;</span>
                </div>
                <form :action=`` method="post">
                    <p>Username</p>
                    <input type="text" placeholder="Username">
                    <p>Password</p>
                    <input type="password" placeholder="Password">
                    <p class="userRegister">Register</p>
                    <button class="modalLogin">Login</button>
                </form>
            </div>
        </div>

        <main>

        </main>

    </div>
</template>
<script>
    Vue.component("app-login", {
        template: "#app-login",
        data: () => ({
            modalActive:false
        }),
        methods:({
            openModal: function () {
            }
        })
        /*,
        created() {
            fetch("/api/users")
                .then(res => res.json())
                .then(res => {
                    this.user = res;
                })
                .catch(() => alert("Error getting user"));
        }*/
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
        background-color: #1dd1a1;
    }
    .button-admin-login{
        background-color: #54a0ff;
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
        position: absolute;
        top: 30%;
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

</style>
