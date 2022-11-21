<template>
    <div>
        <div style="padding: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning"  @click="reset">重置</el-button>
        </div>
        <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
<!--            <el-table-column type="selection" width="55" v-if="user.role != 'ROLE_STUDENT'"></el-table-column>-->
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="name" label="课程名称" width="500px"></el-table-column>
            <el-table-column prop="score" label="学分"></el-table-column>
            <el-table-column prop="times" label="课时"></el-table-column>
            <el-table-column prop="teacher" label="授课老师" v-if="user.role === 'ROLE_STUDENT'"></el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

    </div>
</template>

<script>
    export default {
        name: "MyCourse",
        data(){
            return{
                tableData: [],
                pageNum:1,
                pageSize:5,
                total:0,
                name:"",
                form:{},
                multipleSelection:[],
                headerBg:'headerBg',
                teachers:[],
                user : localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
            }
        },
        created() {
            this.load()
        },
        methods:{
            reset(){
                this.name = ""
                this.load()
            },
            load(){
                // this.request.get("/course/page",{
                //     params:{
                //         pageNum:this.pageNum,
                //         pageSize:this.pageSize,
                //         name:this.name
                //     }}).then(res => {
                //     // console.log(res)
                //     this.tableData = res.records
                //     this.total = res.total
                // })
                this.request.get("/user/role/ROLE_TEACHER").then(res => {
                    // console.log(res.data)
                    this.teachers = res.data
                })
                this.request.get("/course/page/MyCourse/"+this.user.id,{
                    params:{
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        name:this.name
                    }}).then(res => {
                    // console.log(res)
                    this.tableData = res.records
                    this.total = res.total
                })

            },


            handleSelectionChange(val){
                // console.log(val)
                this.multipleSelection = val
            },


            handleSizeChange(pageSize) {
                // console.log(`每页 ${pageSize} 条`);
                this.pageSize = pageSize;
                this.load()
            },
            handleCurrentChange(pageNum) {
                // console.log(`当前页: ${pageNum}`);
                this.pageNum = pageNum
                this.load()
            },

        }
    }
</script>

<style scoped>

</style>