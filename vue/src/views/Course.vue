<template>
    <div>
        <div style="padding: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning"  @click="reset">重置</el-button>
        </div>
        <div style="padding: 10px 0">
<!--            <el-upload action="http://localhost:9090/course/upload" style="display: inline-block" :show-file-list="false" :on-success="handleFileUploadSuccess">-->
<!--                <el-button type="primary" class="ml-5" >上传文件<i class="el-icon-top"></i></el-button>-->
<!--            </el-upload>-->
            <el-button type="primary" @click="handleAdd" v-if="user.role === 'ROLE_ADMIN'">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm
                    class="ml-5"
                    confirm-button-text='确定'
                    cancel-button-text='取消'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定批量删除吗？"
                    @confirm="delBatch"
            >
                <el-button type="danger" slot="reference" v-if="user.role === 'ROLE_ADMIN'">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>

        </div>
        <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" v-if="user.role != 'ROLE_STUDENT'"></el-table-column>
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="name" label="课程名称" width="300px"></el-table-column>
            <el-table-column prop="score" label="学分"></el-table-column>
            <el-table-column prop="times" label="课时"></el-table-column>
            <el-table-column prop="teacher" label="授课老师"></el-table-column>
<!--            <el-table-column label="下载">-->
<!--                <template slot-scope="scope">-->
<!--                    <el-button :disabled="!scope.row.enable" type="primary" @click="download(scope.row.url,scope.row.enable)">下载</el-button>-->
<!--                </template>-->
<!--            </el-table-column>-->
            <el-table-column label="启用"  v-if="user.role === 'ROLE_ADMIN'">
                <template slot-scope="scope">
                    <el-switch v-model="scope.row.state" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
                </template>
            </el-table-column>
<!--            <el-table-column label="启用">-->
<!--                <template slot-scope="scope">-->
<!--                    <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>-->
<!--                </template>-->
<!--            </el-table-column>-->
            <el-table-column label="操作" width="280" align="center" v-if="user.role != 'ROLE_TEACHER'">
                <template slot-scope="scope">
                    <el-button type="primary" @click="selectCourse(scope.row.id)"  v-if="user.role != 'ROLE_TEACHER'">选课 <i class="el-icon-circle-plus-outline"></i></el-button>
                    <el-button type="success" @click="handleEdit(scope.row)" v-if="user.role === 'ROLE_ADMIN'">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="del(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference" v-if="user.role === 'ROLE_ADMIN'">删除 <i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
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
        <el-dialog title="课程信息" :visible.sync="dialogFormVisible">
            <el-form label-width="120px" size="small">
                <el-form-item label="名称" >
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="学分" >
                    <el-input v-model="form.score" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="上课时间" >
                    <el-input v-model="form.times" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="授课老师">
                    <el-select clearable multiple  v-model="form.teacherId" placeholder="请选择授课老师">
<!--                        label为显示的内容  value为存入的数据-->
                        <el-option v-for="item in teachers" :key="item.id" :label="item.nickname" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>

            </el-form>

            <div slot="footer" class="dialog-footer" >
                <el-button @click="dialogFormVisible=false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>

        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "Course",
        data(){
            return{
                tableData: [],
                pageNum:1,
                pageSize:5,
                total:0,
                name:"",
                dialogFormVisible:false,
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
                this.request.get("/course/page",{
                    params:{
                        pageNum:this.pageNum,
                        pageSize:this.pageSize,
                        name:this.name
                    }}).then(res => {
                    // console.log(res)
                    this.tableData = res.records
                    this.total = res.total
                })
                this.request.get("/user/role/ROLE_TEACHER").then(res => {
                    // console.log(res.data)
                    this.teachers = res.data
                })
            },
            handleAdd(){
                this.dialogFormVisible = true
                this.form={}

            },
            handleEdit(row){
                this.form = row
                this.dialogFormVisible = true
            },
            changeEnable(row){
                this.request.post("/course/update/",row).then(res => {
                    if (res == 1){
                        this.$message.success("操作成功")
                        this.load()
                    }else {
                        this.$message.error("操作失败")
                    }
                })
            },
            del(id){
                this.request.get("/course/delete/"+id).then(res => {
                    if (res == 1){
                        this.$message.success("删除成功")
                        this.load()
                    }else {
                        this.$message.error("删除失败")
                    }
                })
            },
            handleSelectionChange(val){
                // console.log(val)
                this.multipleSelection = val
            },
            delBatch(){
                let ids = this.multipleSelection.map(v => v.id)
                // console.log(ids)
                this.request.post("/course/delete/batch",ids).then(res => {
                    if (res >= 1){
                        this.$message.success("批量删除成功")
                        this.load()
                    }else {
                        this.$message.error("批量删除失败")
                    }
                })
            },
            save(){
                this.request.post("/course/save",this.form).then(res => {
                    if (res == 1){
                        this.$message.success("保存成功")
                        this.dialogFormVisible = false
                        this.load()
                    }else {
                        this.$message.error("保存失败")
                    }
                })
            },
            selectCourse(courseId){
                this.request.get("/course/studentCourse/"+courseId+"/"+this.user.id).then(res => {
                    if (res.code === '200'){
                        this.$message.success("选课成功")
                    }else {
                        this.$message.error("选课失败")
                    }
                })
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
            download(url,enable){
                console.log("enable:"+enable)
                if (enable) {
                    window.open(url)
                }
            }
        }
    }
</script>

<style scoped>

</style>