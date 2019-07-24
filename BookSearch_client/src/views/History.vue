<template>
    <v-container grid-list-md text-xs-center>
        <v-layout row wrap>
            <v-flex xs6>
                <v-card>
                    <v-card-title>내 검색 히스토리</v-card-title>
                    <v-list tile>
                        <v-divider></v-divider>
                        <v-list-tile
                                v-for="item in getHistory"
                                :key="item.keywordName">
                            <v-list-tile-content>
                                <v-list-tile-title v-text="item.keywordName"></v-list-tile-title>
                            </v-list-tile-content>
                            <span style="font-size: 12px;">{{item.searchDate.split('T')[0]}}에 검색</span>
                        </v-list-tile>
                    </v-list>
                </v-card>
            </v-flex>
            <v-flex xs6>
                <v-card>
                    <v-card-title>키워드 TOP10</v-card-title>
                    <v-list tile>
                        <v-divider></v-divider>
                        <v-list-tile
                                v-for="item in getKeyword"
                                :key="item.keywordName">
                            <v-list-tile-content>
                                <v-list-tile-title v-text="item.keywordName"></v-list-tile-title>
                            </v-list-tile-content>
                            <v-chip color="orange" text-color="white">
                                {{item.keywordCount}}회
                                <v-icon right>star</v-icon>
                            </v-chip>
                        </v-list-tile>
                    </v-list>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapActions, mapGetters } from 'vuex'
    export default {
      name: "History",
      data() {
        return {
          timer: null,
            historyCount: 0,
            keywordCount: 0,
        }
      },

        computed: {
            ...mapGetters([
                'getHistory',
                'getKeyword'
            ])
        },

      created() {
        this.refresh()
        //this.timer = setInterval(this.refresh, 5000)
      },

      destroyed() {
        //clearInterval(this.timer)
      },

      methods: {
        refresh() {
          this.searchHistory()
          this.searchBestKeyword()
          this.keywordCount = this.getKeyword.length
          this.historyCount = this.getHistory.length
        },
          ...mapActions({
            searchHistory : 'searchHistory',
            searchBestKeyword : 'searchBestKeyword'
          })
      }
    }
</script>

<style scoped>

</style>
