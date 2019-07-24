<template>
    <v-card class="mx-auto" max-width="400" style="margin-top: 200px;">
        <v-card-title>
            <span class="title">로그인</span>
        </v-card-title>
        <v-card-text>
            <form>
                <v-text-field
                        v-model="userId"
                        :counter="20"
                        label="아이디"
                        required
                        @keyup.enter="btnLogin"
                ></v-text-field>
                <v-text-field
                        v-model="userPassword"
                        :counter="12"
                        label="패스워드"
                        required
                        :append-icon="passwordShow ? 'visibility' : 'visibility_off'"
                        @click:append="passwordShow = !passwordShow"
                        :type="passwordShow ? 'text' : 'password'"
                        @keyup.enter="btnLogin"
                ></v-text-field>

                <v-btn @click="btnLogin">로그인</v-btn>
                <v-btn @click="btnRegister">회원가입</v-btn>
            </form>
        </v-card-text>
    </v-card>
</template>

<script>
    import { mapMutations, mapActions } from 'vuex'
    import api from '../api'

    export default {
        name: "Login",
        computed: {

        },
        data() {
            return {
                userId: '',
                userPassword: '',

                passwordShow: false
            }
        },

        methods: {
            btnRegister() {
                this.$router.push("/user/register")
            },
            btnLogin() {
                if(this.userId.length < 1) { alert('아이디를 입력해주세요') }
                else if(this.userPassword.length < 1) { alert('비밀번호를 입력해주세요') }
                else {
                    this.login({userId: this.userId, userPassword: this.userPassword}).then(res => {
                        if(res.status == 200 && !res.data.jwtToken) {
                            alert(res.data.message)
                        } else {
                            api.setHeader(res.data.jwtToken)
                            this.SET_USER_INFO({
                                userId: this.userId,
                                token: res.data.jwtToken
                            })
                            this.$router.push('/')
                        }
                    }).catch(() => {
                        alert('아이디와 비밀번호를 확인해주세요')
                    })
                }
            },
            ...mapActions({
                login: 'login'
            }),
            ...mapMutations({
                SET_USER_INFO: 'SET_USER_INFO'
            })
        }
    }
</script>

<style scoped>

</style>
