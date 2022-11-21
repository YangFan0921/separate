<template>
    <el-container style="min-height: 100vh; ">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(0 21 41 / 35%);">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
      </el-aside>

      <el-container>
          <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
<!--                <Header :collapseBtnClass="collapseBtnClass" :collapse="isCollapse" :currentPathName="currentPathName" :user="user"/>-->
                  <div style="flex: 1; font-size: 20px">
                      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>
                      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px" >
                          <el-breadcrumb-item :to="'/home'">首页</el-breadcrumb-item>
                          <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
                      </el-breadcrumb>
                </div>
                  <el-dropdown style="width: 100px;text-align: right; cursor: pointer">
                      <div style="display: inline-block">
                          <img :src="user.avatarUrl" alt=""
                               style="width: 30px;height: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
                          <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
                      </div>
                      <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
                          <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                              <router-link to="/person" style="text-decoration: none;color: #606266">个人信息</router-link>
                          </el-dropdown-item>
                          <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                              <span style="text-decoration: none;color: #606266" @click="logout">退出</span>
                          </el-dropdown-item>
                      </el-dropdown-menu>
                  </el-dropdown>
        </el-header>

        <el-main>
            <router-view @refreshUser="getUser"/>
        </el-main>
      </el-container>
    </el-container>

</template>

<script>


    import Aside from "@/components/Aside";
    import Header from "@/components/Header";

    export default {
        components: {Aside, Header},
        data() {
            return {
                collapseBtnClass: 'el-icon-s-fold',
                isCollapse: false,
                sideWidth: 200,
                logoTextShow: true,
                // currentPathName:"currentPathName",
                user:{}
            }
        },
        computed: {
            currentPathName () {
                return this.$store.state.currentPathName;　　//需要监听的数据
            }
        },
        watch: {
            currentPathName (newVal, oldVal) {
                // console.log(newVal)
            }
        },
        created() {
            this.getUser();
        },
        methods: {
            collapse() {  // 点击收缩按钮触发
                this.isCollapse = !this.isCollapse
                if (this.isCollapse) {  // 收缩
                    this.sideWidth = 64
                    this.collapseBtnClass = 'el-icon-s-unfold'
                    this.logoTextShow = false
                } else {   // 展开
                    this.sideWidth = 200
                    this.collapseBtnClass = 'el-icon-s-fold'
                    this.logoTextShow = true
                }
             },
            getUser(){
                let id =localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).id : null
                // console.log(JSON.parse(localStorage.getItem("user")))
                if (id){
                    this.request.get("/user/me/"+id).then(res => {
                        //重新赋值后台最新user数据
                        // console.log("getUser---->",res)
                        this.user = res
                    })
                }
            },
            logout(){
                this.$store.commit("logout")
                this.$message.success("退出成功")
            }
        }
    }
</script>




