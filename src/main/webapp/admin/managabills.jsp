<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="layout/head.jsp"></jsp:include>

</head>
<body>
	<div id="main-wrapper">
		<!-- Thêm phần tiêu đề trang -->
		<jsp:include page="layout/header.jsp"></jsp:include>
		<!-- Xong phần tiêu đề trang -->
		<div class="content-body">
			<div class="container-fluid">
				<div class="row page-titles mx-0">
					<div class="col-sm-6 p-md-0">
						<div class="welcome-text">
							<h4>Xin chào bạn</h4>
							<span class="ml-1">Danh Sách Hóa Đơn</span>
						</div>
					</div>
					<div
						class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="javascript:void(0)">Quản trị</a></li>
							<li class="breadcrumb-item active"><a
								href="javascript:void(0)">Hóa đơn</a></li>
						</ol>
					</div>
				</div>
				<!-- row -->


				<div class="row">
					<div class="col-12">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">Datatable</h4>
							</div>
							<div class="card-body">
								<div class="table-responsive">
									<table id="datatable-bills" class="display"
										style="width: 100%; color: black; font-size: 13px;">
										<thead>
											<tr>
												<th>stt</th>
												<th>Người nhận</th>
												<th>Địa chỉ</th>
												<th>Thành phố</th>
												<th>SDT</th>
												<th>Email</th>
												<th>Ghi chú</th>
												<th>Thời gian</th>
												<th>Tổng tiền</th>
												<th>#</th>
												<th>#</th>

											</tr>
										</thead>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Thêm chân trang -->
		<jsp:include page="layout/footer.jsp"></jsp:include>
		<!-- Xong thêm chân trang -->

	</div>
	<script type="text/javascript">
		var json =
	<%=request.getAttribute("jsonBills")%>
		;
		$('#datatable-bills')
				.DataTable(
						{
							data : json,
							columns : [
									{
										data : 'id'
									},
									{
										data : 'nameRecevie'
									},
									{
										data : 'numberHouse'
									},
									{
										data : 'address'
									},
									{
										data : 'numberPhone'
									},
									{
										data : 'email'
									},
									{
										data : 'note'
									},
									{
										data : 'datePay'
									},
									{
										data : 'total'
									},
									{
										data : 'status',
										render : function(data) {
												return "<button type='button'  class='btn btn-sm btn-primary'>Đã thanh toán</button>";
											}
									},

									{
										data : 'id',
										render : function(data) {
											return "<button type='button' class='btn btn-sm btn-primary' onclick='deleteBills("
													+ data + ")'>Xóa</button>"
										}
									},

							],
							"pageLength" : 15
						});
		function deleteBills(id) {
			if (confirm("Bạn muốn xóa hóa đơn này"))
				$.ajax({
					url : "AdminBillsController?idD=" + id,
					type : 'Get',
					success : function(reponse) {
						console.log(reponse);
						alert("Đã xóa thành công ");
						window.location.href = "AdminManagaBillController";
					}

				})
		}
		
	</script>

</body>
</html>