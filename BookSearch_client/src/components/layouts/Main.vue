<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title class="headline text-uppercase">
                <router-link :to="'/'" style="color: #222222; text-decoration: none;">The BOOK STORE</router-link>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn @click="btnKeyword" flat target="_blank" >
                <span class="mr-2">키워드 히스토리</span>
            </v-btn>
            <v-btn @click="btnLogout" flat target="_blank" >
                <span class="mr-2">로그아웃</span>
            </v-btn>
        </v-toolbar>
        <v-content>
            <v-container>
                <router-view/>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        name: "Main",

        methods: {
            btnKeyword() {
                this.$router.push('/history')
            },
            btnLogout() {
                try {
                    this.logout()
                } finally{
                    this.$router.push('/user/login')
                }
            },
            ...mapActions({
                logout: 'logout'
            })
        }
    }
</script>

<style scoped>

</style>
