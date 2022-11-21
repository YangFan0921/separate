<template>
    <div>
        <div style="padding: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
            <el-button type="primary" class="ml-5" @click="load">搜索</el-button>
            <el-button type="warning"  @click="reset">重置</el-button>
        </div>
        <div style="padding: 10px 0">
            <el-upload action="http://localhost:9090/file/upload" style="display: inline-block" :show-file-list="false" :on-success="handleFileUploadSuccess">
                <el-button type="primary" class="ml-5" >上传文件<i class="el-icon-top"></i></el-button>
            </el-upload>
            <el-popconfirm
                    class="ml-5"
                    confirm-button-text='确定'
                    cancel-button-text='取消'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定批量删除吗？"
                    @confirm="delBatch"
            >
                <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>

        </div>
        <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column><el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="name" label="文件名称"></el-table-column>
            <el-table-column prop="type" label="文件类型"></el-table-column>
            <el-table-column prop="size" label="文件大小(Kb)"></el-table-column>
            <el-table-column label="下载">
                <template slot-scope="scope">
                    <el-button :disabled="!scope.row.enable" type="primary" @click="download(scope.row.url,scope.row.enable)">下载</el-button>
                </template>
            </el-table-column>
            <el-table-column label="启用">
                <template slot-scope="scope">
                    <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="del(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference" >删除 <i class="el-icon-remove-outline"></i></el-button>
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
    </div>
</template>

<script>
    export default {
        name: "File",
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
                this.request.get("/file/page",{
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
            changeEnable(row){
                this.request.post("/file/update/",row).then(res => {
                    if (res == 1){
                        this.$message.success("操作成功")
                        this.load()

                    }
                })
            },
            del(id){
                this.request.get("/file/delete/"+id).then(res => {
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
                this.request.post("/file/delete/batch",ids).then(res => {
                    if (res >= 1){
                        this.$message.success("批量删除成功")
                        this.load()
                    }else {
                        this.$message.error("批量删除失败")
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
            handleFileUploadSuccess(res){
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