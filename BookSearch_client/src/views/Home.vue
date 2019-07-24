<template>
    <v-flex>
      <v-text-field
              solo
              v-model="keyword"
              label="키워드 입력 후 Enter"
              append-icon="search"
              @keyup.enter="searchWithoutPage"></v-text-field>
        <div v-if="getBookList.total == 0" style="min-height: 300px; text-align:center;">
            <h2 style="margin-top: 130px; color: #bbbbbb;">데이터가 없습니다</h2>
        </div>
        <v-card v-else>
            <v-list three-line>
                <template v-for="(item, index) in getBookList.bookList">

                    <v-divider
                            v-if="index != 0"
                            :inset="true"
                    ></v-divider>

                    <v-list-tile
                            :key="index"
                            avatar @click="showDetail(index)">
                        <v-list-tile-avatar>
                            <img v-if="item.thumbnail" :src="item.thumbnail">
                        </v-list-tile-avatar>

                        <v-list-tile-content>
                            <v-list-tile-title>{{item.title}}<span style="font-size: 12px;"> (ISBN : {{ item.isbn }})</span></v-list-tile-title>
                            <v-list-tile-sub-title class="text--primary"></v-list-tile-sub-title>
                            <v-list-tile-sub-title>저 : {{authors(item.authors)}} {{item.translators.length != 0 ? '/ 역 : ' + authors(item.translators):''}}</v-list-tile-sub-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </template>
            </v-list>
        </v-card>
        <div class="text-xs-center" style="margin-top: 20px;" v-if="!getBookList.total == 0">
            <v-pagination
                    @input="search"
                    @previous="search"
                    @next="search"
                    v-model="getBookList.page"
                    :length="Math.floor(getBookList.total/10)+1"
            ></v-pagination>
        </div>
        <v-dialog v-model="detail" width="700px">
            <v-card v-if="detail">
                <v-card-title>
                    <span class="headline">{{getBookList.bookList[detailIndex].title}}</span>
                </v-card-title>
                <v-card-text>
                    <v-layout row wrap>
                        <v-flex xs2>
                            <v-card dark color="primary">
                                <v-img :src="getBookList.bookList[detailIndex].thumbnail"></v-img>
                            </v-card>
                        </v-flex>
                        <v-flex xs4 style="padding-left: 20px;">
                            <h3>저자</h3>
                            <p>{{authors(getBookList.bookList[detailIndex].authors)}}{{getBookList.bookList[detailIndex].translators.length?'(번역: ' + authors(getBookList.bookList[detailIndex].translators) + ')':''}}</p>
                            <h3>출판사</h3>
                            <p>{{getBookList.bookList[detailIndex].publisher}}</p>
                            <h3>상태</h3>
                            <p>{{getBookList.bookList[detailIndex].status}}</p>
                        </v-flex>
                        <v-flex xs4 style="padding-left: 12px;">
                            <h3>가격</h3>
                            <p>{{getBookList.bookList[detailIndex].price}}원 (할인가 : {{getBookList.bookList[detailIndex].salePrice + '원'}})</p>
                            <h3>등록일</h3>
                            <p>{{getBookList.bookList[detailIndex].datetime.split('T')[0]}}</p>
                            <h3>ISBN</h3>
                            <p>{{getBookList.bookList[detailIndex].isbn}}</p>
                        </v-flex>
                    </v-layout>
                    <h3 style="margin-top: 12px;">책 소개</h3>
                    <p style="margin-top: 3px;">{{getBookList.bookList[detailIndex].contents}}</p>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="green darken-1" flat="flat" @click="detail = false; detailIndex = null;">닫기</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-flex>
</template>

<script>
  import { mapActions, mapGetters, mapMutations } from 'vuex'

  export default {
    components: {},

    data() {
      return {
          keyword: '',
          detail: false,
          detailIndex: null
      }
    },

    computed: {
      ...mapGetters({
        getBookList: 'getBookList'
      })
    },

    methods: {
        authors(arr) {
            let author = ''
            arr.forEach(item => { author += (', ' + item) })
            return author.replace(', ','')
        },
        searchWithoutPage() {
            if(!this.keyword) { alert('검색어를 입력해주세요') }
            else {
              this.searchByKeyword({query: this.keyword}).then(res => {
                  const data = {
                      keyword: this.keyword,
                      page: 1,
                      size: 10,
                      total: res.data.totalCount,
                      bookList: res.data.bookList
                  }
                  this.SET_SEARCH_RESULT_BOOKS(data)
              }).catch(() => {
                  this.logout()
                  this.$router.push('/user/login')
              })
            }
          },
        search(page){
            this.searchByKeyword({query: this.keyword, page: page, size: 10}).then(res => {
                const data = {
                    keyword: this.keyword,
                    page: page,
                    size: 10,
                    total: res.data.totalCount,
                    bookList: res.data.bookList
                }
                this.SET_SEARCH_RESULT_BOOKS(data)
            }).catch(() => {
                this.logout()
                this.$router.push('/user/login')
            })
        },
        showDetail(index) {
            this.detail = true
            this.detailIndex = index
        },

        ...mapMutations({
            SET_SEARCH_RESULT_BOOKS: 'SET_SEARCH_RESULT_BOOKS'
        }),
        ...mapActions({
            searchByKeyword: 'bookSearch',
            logout: 'logout'
        })
    }
  }
</script>
