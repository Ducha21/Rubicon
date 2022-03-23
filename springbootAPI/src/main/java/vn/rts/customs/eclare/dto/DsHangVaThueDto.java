package vn.rts.customs.eclare.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Data
public class DsHangVaThueDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("mã ngoài hạn ngạch")
    @Size(max = 1)
    private String maNgoaiHanNgach;

    @ApiModelProperty("Mã số hàng hóa (HS)")
    @Size(max = 12)
    private String maHs;

    @ApiModelProperty("Mã quản lý riêng")
    @Size(max = 20)
    private String maQlr;

    @ApiModelProperty("thuế suất")
    private BigDecimal thueSuat;

    @ApiModelProperty("Mức thuế tuyệt đối")
    private BigDecimal thueSuatTuyetDoi;

    @ApiModelProperty("Mã đơn vị tính của mức thuế tuyệt đối")
    @Size(max = 4)
    private String thueSuatTuyetDoiDvt;

    @ApiModelProperty("Mã nguyên tệ của mức thuế tuyệt đối")
    @Size(max = 3)
    private String thueSuatTuyetDoiMaNt;

    @ApiModelProperty("Mã nước xuất xứ")
    @Size(max = 20)
    private String nuocXxMa;

    @ApiModelProperty("Mã hàng hóa (Commodity codes)")
    @Size(max = 50)
    private String maHang;

    @ApiModelProperty("Mô tả hàng hóa")
    @Size(max = 500)
    private String tenHang;

    @ApiModelProperty("Mã miễn / Giảm / Không chịu thuế xuất khẩu")
    @Size(max = 5)
    private String thueMienGiamMa;

    @ApiModelProperty("Số tiền giảm thuế xuất khẩu")
    private BigDecimal thueGiam;

    @ApiModelProperty("Số lượng (1)")
    private BigDecimal luong;

    @ApiModelProperty("Mã đơn vị tính của Số lượng (1)")
    @Size(max = 4)
    private String maDvt;

    @ApiModelProperty("Số lượng (2)")
    private BigDecimal luong2;

    @ApiModelProperty("Mã đơn vị tính của Số lượng (2)")
    @Size(max = 20)
    private String maDvt2;

    @ApiModelProperty("Trị giá hóa đơn")
    private BigDecimal triGiaKb;

    @ApiModelProperty("Đơn giá hóa đơn")
    private BigDecimal dgiaKb;

    @ApiModelProperty("Mã nguyên tệ (của đơn giá hóa đơn)")
    @Size(max = 3)
    private String dgiaKbMaNt;

    @ApiModelProperty("Mã đơn vị tính ( của đơn giá hóa đơn)")
    @Size(max = 4)
    private String dgiaKbDvt;

    @ApiModelProperty("Trị giá tính thuế")
    private BigDecimal triGiaTinhThue;

    @ApiModelProperty("Trị giá tính thuế đơn vị")
    @Size(max = 20)
    private String triGiaTinhThueDvt;

    @ApiModelProperty("Số thứ tự của dòng hàng trên tờ khai tạm nhập tái xuất tương ứng")
    @Size(max = 20)
    private String sttHangTntx;

    @ApiModelProperty("Danh mục miễn thuế xuất khẩu")
    @Size(max = 12)
    private String soDkMienThue;

    @ApiModelProperty("Số dòng tương ứng trong Danh mục miễn thuế xuất khẩu")
    @Size(max = 3)
    private String sttHangDmMienThue;

    @ApiModelProperty("Chế độ ưu đãi thuế( Loại C/O(Form A, D, E, AK))")
    @Size(max = 50)
    private String cdUdThue;

    @ApiModelProperty("id mẫu khai báo hải quan")
    @Size(max = 36)
    private String mauKbHqId;

    @ApiModelProperty("người tạo")
    @Size(max = 50)
    private String nguoiTao;

    @ApiModelProperty("ngày tạo")
    private Date ngayTao;

    @ApiModelProperty("người sửa")
    @Size(max = 50)
    private String nguoiSua;

    @ApiModelProperty("ngày sửa")
    private Date ngaySua;

    @ApiModelProperty("1.82. Mã áp dụng mức thuế tuyệt đối")
    @Size(max = 10)
    private String maMucThueTuyetDoi;

    @ApiModelProperty("mã biểu thuế")
    @Size(max = 20)
    private String maBieuThue;

    @ApiModelProperty("trị giá khai báo vnd")
    @Size(max = 3)
    private String triGiaKbVnd;

    @ApiModelProperty("số danh mục khoản điều chỉnh")
    @Size(max = 300)
    private String soDanhMucKhoanDieuChinh;

    @ApiModelProperty("số thứ tự hàng")
    private Integer sttHang;

    @ApiModelProperty("ghi chú khác")
    @Size(max = 2000)
    private String ghiChuKhac;

    @ApiModelProperty("mã doanh nghiệp")
    @Size(max = 36)
    private String maDoanhNghiep;

    @ApiModelProperty("danh mục hàng hóa tờ khai id")
    private Long dmHangHoaTkId5;

    @ApiModelProperty("số thứ tự")
    private Integer stt5;

    @ApiModelProperty("Mã áp dụng thuế suất / Mức thuế và thu khác")
    @Size(max = 10)
    private String thueMst5;

    @ApiModelProperty("mã miễn thuế")
    @Size(max = 5)
    private String maMienThue5;

    @ApiModelProperty("số tiền giảm thuế")
    private BigDecimal soTienGiamThue5;

    @ApiModelProperty("danh mục hàng hóa tờ khai id")
    private Long dmHangHoaTkId1;

    @ApiModelProperty("số thứ tự")
    private Integer stt1;

    @ApiModelProperty("Mã áp dụng thuế suất / Mức thuế và thu khác")
    @Size(max = 10)
    private String thueMst1;

    @ApiModelProperty("mã miễn thuế")
    @Size(max = 5)
    private String maMienThue1;

    @ApiModelProperty("số tiền giảm thuế")
    private BigDecimal soTienGiamThue1;

    @ApiModelProperty("danh mục hàng hóa tờ khai id")
    private Long dmHangHoaTkId2;

    @ApiModelProperty("số thứ tự")
    private Integer stt2;

    @ApiModelProperty("Mã áp dụng thuế suất / Mức thuế và thu khác")
    @Size(max = 10)
    private String thueMst2;

    @ApiModelProperty("mã miễn thuế")
    @Size(max = 5)
    private String maMienThue2;

    @ApiModelProperty("số tiền giảm thuế")
    private BigDecimal soTienGiamThue2;

    @ApiModelProperty("danh mục hàng hóa tờ khai id")
    private Long dmHangHoaTkId3;
    private Integer stt3;

    @ApiModelProperty("Mã áp dụng thuế suất / Mức thuế và thu khác")
    @Size(max = 10)
    private String thueMst3;

    @ApiModelProperty("mã miễn thuế")
    @Size(max = 5)
    private String maMienThue3;

    @ApiModelProperty("số tiền giảm thuế")
    private BigDecimal soTienGiamThue3;

    @ApiModelProperty("danh mục hàng hóa tờ khai id")
    private Long dmHangHoaTkId4;

    @ApiModelProperty("số thứ tự")
    private Integer stt4;

    @ApiModelProperty("Mã áp dụng thuế suất / Mức thuế và thu khác")
    @Size(max = 10)
    private String thueMst4;

    @ApiModelProperty("mã miễn thuế")
    @Size(max = 5)
    private String maMienThue4;

    @ApiModelProperty("số tiền giảm thuế")
    private BigDecimal soTienGiamThue4;
}
