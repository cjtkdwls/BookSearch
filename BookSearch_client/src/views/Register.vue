<template>
    <v-card class="mx-auto" max-width="400" style="margin-top: 200px;">
        <v-card-title>
            <span class="title">회원가입</span>
        </v-card-title>
        <v-card-text>
            <form>
                <v-text-field
                        v-model="userId"
                        :counter="20"
                        label="아이디"
                        required
                        @keyup.enter="btnRegister"
                ></v-text-field>
                <v-text-field
                        v-model="userPassword"
                        :counter="12"
                        label="패스워드"
                        required
                        :append-icon="passwordShow ? 'visibility' : 'visibility_off'"
                        @click:append="passwordShow = !passwordShow"
                        :type="passwordShow ? 'text' : 'password'"
                        @keyup.enter="btnRegister"
                ></v-text-field>
                <v-text-field
                        v-model="userPasswordCheck"
                        :counter="12"
                        label="패스워드 확인"
                        required
                        :append-icon="passwordCheckShow ? 'visibility' : 'visibility_off'"
                        @click:append="passwordCheckShow = !passwordCheckShow"
                        :type="passwordShow ? 'text' : 'password'"
                        @keyup.enter="btnRegister"
                ></v-text-field>

                <v-btn @click="btnRegister">회원가입</v-btn>
                <v-btn @click="btnGoBack">로그인으로 돌아가기</v-btn>
            </form>
        </v-card-text>
    </v-card>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        name: "Register",
        data() {
            return {
                userId: '',
                userPassword: '',
                userPasswordCheck: '',

                passwordShow: false,
                passwordCheckShow: false,
            }
        },
        methods: {
            btnRegister() {
                if(this.userPassword != this.userPasswordCheck) { alert('비밀번호가 서로 다릅니다') }
                else if(this.userId.length < 6) { alert('6자 이상의 아이디를 입력해주세요') }
                else if(this.userPassword.length < 6) { alert('6자리 이상의 비밀번호를 입력해주세요') }
                else {
                    this.register({ userId: this.userId, userPassword: this.userPassword})
                        .then(res => {
                            if(res.status == 200) {
                                alert(res.data.message)
                                this.$router.push('/user/login')
                            }
                        })
                        .catch(() => {
                            alert('이미 존재하는 계정입니다')
                        })
                }
            },
            btnGoBack() {
                this.$router.push("/user/login")
            },
            ...mapActions({
                register: 'register'
            }),
        }
    }
</script>

<style scoped>

</style>
