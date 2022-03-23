
package vn.rts.customs.eclare.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import vn.rts.customs.eclare.entity.MauToKhaiHqEntity;
import vn.rts.customs.eclare.request.search.KeySearchMauToKhaiHq;
import vn.rts.customs.eclare.request.search.KeyValueDto;
import vn.rts.customs.eclare.util.VnaccsConvert;
import vn.rts.customs.lib.dao.BaseRepositoryImplEx;

/**
 * @author VNETC
 */

@Repository
public class MauToKhaiHqRepositoryImpl extends BaseRepositoryImplEx<MauToKhaiHqEntity, String> {

	@Override
	public MauToKhaiHqEntity insert(MauToKhaiHqEntity objInput) {
		UUID uuid = UUID.randomUUID();
		objInput.setId(uuid.toString());
		return excuteObjectUsingSp( //
				MauToKhaiHqEntity.class //
				, "PKG_MAU_TO_KHAI_HQ.insertItem" //
				, objInput.getId() //
				, objInput.getBaoLanhKiHieuChungTu() //
				, objInput.getBaoLanhMaNganHang() //
				, objInput.getBaoLanhNamPhatHanh() //
				, objInput.getBaoLanhSoChungTu() //
				, objInput.getBhMaLoaiPhi() //
				, objInput.getBhMaTienTe() //
				, objInput.getBhPhi() //
				, objInput.getChiTietTkTriGia() //
				, objInput.getCoQuanHq() //
				, objInput.getDcTriGiaTongHsPhanBo() //
				, objInput.getDiaChiNguoiNk() //
				, objInput.getDiaChiXk1() //
				, objInput.getDiaChiXk2() //
				, objInput.getDiaChiXk3() //
				, objInput.getDiaChiXk4() //
				, objInput.getDiaDiemDichVcbt() //
				, objInput.getDiaDiemLuuKhoHangCtqdk() //
				, objInput.getDoanhNghiepId() //
				, VnaccsConvert.string2Date(objInput.getGcNgayHopDong(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, objInput.getGcSoHopDong() //
				, objInput.getGhiChu() //
				, objInput.getHanMucKiHieuChungTu() //
				, objInput.getHanMucNamPhatHanh() //
				, objInput.getHanMucSoChungTu() //
				, objInput.getHoaDonDieuKienGia() //
				, objInput.getHoaDonMaTienTe() //
				, VnaccsConvert.string2Date(objInput.getHoaDonNgayPhatHanh(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, objInput.getHoaDonPlGia() //
				, objInput.getHoaDonPlHinhThuc() //
				, objInput.getHoaDonPtThanhToan() //
				, objInput.getHoaDonSo() //
				, objInput.getHoaDonSoDienTu() //
				, objInput.getHoaDonTongTriGiaKb() //
				, objInput.getKenhKhaiBao() //
				, objInput.getKetQuaXuLy() //
				, objInput.getKyHieuSoHieu() //
				, objInput.getLoaiTkHq() //
				, objInput.getLuongToKhai() //
				, objInput.getLyDoTuChoi() //
				, objInput.getMaBuuChinhNguoiNk() //
				, objInput.getMaBuuChinhNguoiXk() //
				, objInput.getMaChucNang() //
				, objInput.getMaDaiLyHq() //
				, objInput.getMaDiaDiemDoHang() //
				, objInput.getMaDiaDiemNhanHangCuoi() //
				, objInput.getHaiQuanTiepNhanKb() //
				, objInput.getMaLoaiHinh() //
				, objInput.getMaLyDoDnbp() //
				, objInput.getMaNganHangTraThueThay() //
				, objInput.getMaNguoiKhaiHq() //
				, objInput.getMaNguoiNk() //
				, objInput.getMaNguoiUyThacNk() //
				, objInput.getMaNguoiUyThacXk() //
				, objInput.getMaNguoiXk() //
				, objInput.getMaNuocXk() //
				, objInput.getMaPhuongTienVanChuyen() //
				, objInput.getMaPtVanChuyen() //
				, VnaccsConvert.string2Date(objInput.getNgayDen(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayDenDuKien(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayHangDiDuKien(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayHoanThanhNvThue(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayKhaiDuKien(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayKhoiHanh(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayNhapKhoDauTien(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, objInput.getNgaySua()
				, objInput.getNgayTraLoi()
				, objInput.getNgayTao()
				, objInput.getNguoiNopThue() //
				, objInput.getNguoiSua() //
				, objInput.getNguoiTao() //
				, objInput.getNhomLoaiHinh() //
				, objInput.getPhanLoaiChiThiHq() //
				, objInput.getPlCaNhanToChuc() //
				, objInput.getPlHangHoa() //
				, objInput.getPlKths() //
				, objInput.getSdtNguoiNk() //
				, objInput.getSoContainer() //
				, objInput.getTongSoLuongKien() //
				, objInput.getTongSoLuongKienDvt() //
				, objInput.getSoLuongKien() //
				, objInput.getSoLuongKienDvt() //
				, objInput.getSoNhanhTk() //
				, objInput.getSoQuanLyNbdn() //
				, objInput.getSoThamChieuTk() //
				, objInput.getSoTiepNhan() //
				, objInput.getSoTk() //
				, objInput.getSoTkDauTien() //
				, objInput.getSoTkXktc() //
				, objInput.getSoVanDon() //
				, objInput.getTenDiaDiemDoHang() //
				, objInput.getTenDiaDiemNhanHangCuoi() //
				, objInput.getTenNguoiKhaiHq() //
				, objInput.getTenNguoiNk() //
				, objInput.getTenNguoiUyThacNk() //
				, objInput.getTenNguoiUyThacXk() //
				, objInput.getTenNguoiXk() //
				, objInput.getTenPhuongTienVanChuyen() //
				, objInput.getTgGiaCoSoHc() //
				, objInput.getTgMaPlTk() //
				, objInput.getTgMaTienTe() //
				, objInput.getTgSoTiepNhanTkth() //
				, VnaccsConvert.string2Date(objInput.getThoiHanTaiXuat(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getThoiHanTaiNhap(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, objInput.getTongSoTk() //
				, objInput.getTongTrongLuong() //
				, objInput.getTongTrongLuongDvt() //
				, objInput.getVcMaLoaiPhi() //
				, objInput.getVcMaTienTePhi() //
				, objInput.getVcPhi() //
				, objInput.getMaKetQuaKiemTraNd() //
				, objInput.getDiaChiNguoiXk() //
				, objInput.getDiaChiNk1() //
				, objInput.getDiaChiNk2() //
				, objInput.getDiaChiNk3() //
				, objInput.getDiaChiNk4() //
				, objInput.getPlKhongCanQuyDoiVND() //
				, objInput.getSdtNguoiXk() //
				, objInput.getSoDangKyBaoHiemTongHop() //
				, objInput.getSoTKXKTaiChoTuongUng() //
				, objInput.getTongTriGiaTinhThue() //
				, objInput.getTongTriGiaTinhThueMaTienTe() //
				, objInput.getMauToKhaiHqIdThamChieu() //
				, objInput.getMaDoanhNghiep()
				, objInput.getKbSoDinhDanhCangBien()
				, objInput.getKbSoDinhDanhCangHK()
				, objInput.getMaXacDinhThoiHanNopThue()
				, objInput.getMaDiaDiemXepHang()
				, objInput.getTenDiaDiemXepHang()
				, objInput.getMaVb()
				, objInput.getMaNuocNk()
				, objInput.getTenDoanhNghiep()
				, objInput.getTenHaiQuanTiepNhanKb()
		);
	}

	@Override
	public MauToKhaiHqEntity update(MauToKhaiHqEntity objInput) {
		return excuteObjectUsingSp( //
				MauToKhaiHqEntity.class //
				, "PKG_MAU_TO_KHAI_HQ.updateItem" //
				, objInput.getId() //
				, objInput.getBaoLanhKiHieuChungTu() //
				, objInput.getBaoLanhMaNganHang() //
				, objInput.getBaoLanhNamPhatHanh() //
				, objInput.getBaoLanhSoChungTu() //
				, objInput.getBhMaLoaiPhi() //
				, objInput.getBhMaTienTe() //
				, objInput.getBhPhi() //
				, objInput.getChiTietTkTriGia() //
				, objInput.getCoQuanHq() //
				, objInput.getDcTriGiaTongHsPhanBo() //
				, objInput.getDiaChiNguoiNk() //
				, objInput.getDiaChiXk1() //
				, objInput.getDiaChiXk2() //
				, objInput.getDiaChiXk3() //
				, objInput.getDiaChiXk4() //
				, objInput.getDiaDiemDichVcbt() //
				, objInput.getDiaDiemLuuKhoHangCtqdk() //
				, objInput.getDoanhNghiepId() //
				, VnaccsConvert.string2Date(objInput.getGcNgayHopDong(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, objInput.getGcSoHopDong() //
				, objInput.getGhiChu() //
				, objInput.getHanMucKiHieuChungTu() //
				, objInput.getHanMucNamPhatHanh() //
				, objInput.getHanMucSoChungTu() //
				, objInput.getHoaDonDieuKienGia() //
				, objInput.getHoaDonMaTienTe() //
				, VnaccsConvert.string2Date(objInput.getHoaDonNgayPhatHanh(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, objInput.getHoaDonPlGia() //
				, objInput.getHoaDonPlHinhThuc() //
				, objInput.getHoaDonPtThanhToan() //
				, objInput.getHoaDonSo() //
				, objInput.getHoaDonSoDienTu() //
				, objInput.getHoaDonTongTriGiaKb() //
				, objInput.getKenhKhaiBao() //
				, objInput.getKetQuaXuLy() //
				, objInput.getKyHieuSoHieu() //
				, objInput.getLuongToKhai() //
				, objInput.getLyDoTuChoi() //
				, objInput.getMaBuuChinhNguoiNk() //
				, objInput.getMaBuuChinhNguoiXk() //
				, objInput.getMaChucNang() //
				, objInput.getMaDaiLyHq() //
				, objInput.getMaDiaDiemDoHang() //
				, objInput.getMaDiaDiemNhanHangCuoi() //
				, objInput.getHaiQuanTiepNhanKb() //
				, objInput.getMaLoaiHinh() //
				, objInput.getMaLyDoDnbp() //
				, objInput.getMaNganHangTraThueThay() //
				, objInput.getMaNguoiKhaiHq() //
				, objInput.getMaNguoiNk() //
				, objInput.getMaNguoiUyThacNk() //
				, objInput.getMaNguoiUyThacXk() //
				, objInput.getMaNguoiXk() //
				, objInput.getMaNuocXk() //
				, objInput.getMaPhuongTienVanChuyen() //
				, objInput.getMaPtVanChuyen() //
				, VnaccsConvert.string2Date(objInput.getNgayDen(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayDenDuKien(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayHangDiDuKien(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayHoanThanhNvThue(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayKhaiDuKien(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayKhoiHanh(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getNgayNhapKhoDauTien(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, objInput.getNgaySua()
				, objInput.getNgayTraLoi()
				, objInput.getNguoiNopThue() //
				, objInput.getNguoiSua() //
				, objInput.getNguoiTao() //
				, objInput.getNhomLoaiHinh() //
				, objInput.getPhanLoaiChiThiHq() //
				, objInput.getPlCaNhanToChuc() //
				, objInput.getPlHangHoa() //
				, objInput.getPlKths() //
				, objInput.getSdtNguoiNk() //
				, objInput.getSoContainer() //
				, objInput.getTongSoLuongKien() //
				, objInput.getTongSoLuongKienDvt() //
				, objInput.getSoLuongKien() //
				, objInput.getSoLuongKienDvt() //
				, objInput.getSoNhanhTk() //
				, objInput.getSoQuanLyNbdn() //
				, objInput.getSoThamChieuTk() //
				, objInput.getSoTiepNhan() //
				, objInput.getSoTk() //
				, objInput.getSoTkDauTien() //
				, objInput.getSoTkXktc() //
				, objInput.getSoVanDon() //
				, objInput.getTenDiaDiemDoHang() //
				, objInput.getTenDiaDiemNhanHangCuoi() //
				, objInput.getTenNguoiKhaiHq() //
				, objInput.getTenNguoiNk() //
				, objInput.getTenNguoiUyThacNk() //
				, objInput.getTenNguoiUyThacXk() //
				, objInput.getTenNguoiXk() //
				, objInput.getTenPhuongTienVanChuyen() //
				, objInput.getTgGiaCoSoHc() //
				, objInput.getTgMaPlTk() //
				, objInput.getTgMaTienTe() //
				, objInput.getTgSoTiepNhanTkth() //
				, VnaccsConvert.string2Date(objInput.getThoiHanTaiXuat(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, VnaccsConvert.string2Date(objInput.getThoiHanTaiNhap(), VnaccsConvert.EFormatDateTime.YYYY_MM_DD) //
				, objInput.getTongSoTk() //
				, objInput.getTongTrongLuong() //
				, objInput.getTongTrongLuongDvt() //
				, objInput.getVcMaLoaiPhi() //
				, objInput.getVcMaTienTePhi() //
				, objInput.getVcPhi() //
				, objInput.getMaKetQuaKiemTraNd() //
				, objInput.getDiaChiNguoiXk() //
				, objInput.getDiaChiNk1() //
				, objInput.getDiaChiNk2() //
				, objInput.getDiaChiNk3() //
				, objInput.getDiaChiNk4() //
				, objInput.getPlKhongCanQuyDoiVND() //
				, objInput.getSdtNguoiXk() //
				, objInput.getSoDangKyBaoHiemTongHop() //
				, objInput.getSoTKXKTaiChoTuongUng() //
				, objInput.getTongTriGiaTinhThue() //
				, objInput.getTongTriGiaTinhThueMaTienTe() //
				, objInput.getKbSoDinhDanhCangBien()
				, objInput.getKbSoDinhDanhCangHK()
				, objInput.getMaXacDinhThoiHanNopThue()
				, objInput.getMaDiaDiemXepHang()
				, objInput.getTenDiaDiemXepHang()
				, objInput.getMaVb()
				, objInput.getMaNuocNk()
				, objInput.getTenDoanhNghiep()
				, objInput.getTenHaiQuanTiepNhanKb()
		);
	}

	@Override
	public void delete(MauToKhaiHqEntity objInput) {
		excuteInsertUpdateDeleteUsingSp( //
				"PKG_MAU_TO_KHAI_HQ.deleteItem" //
				, objInput.getId() //
		);
	}

	@Override
	public Optional<MauToKhaiHqEntity> findById(String id) {
		return Optional.ofNullable(excuteObjectUsingSp(MauToKhaiHqEntity.class //
				, "PKG_MAU_TO_KHAI_HQ.findById" //
				, id //
		));
	}

	public Page<MauToKhaiHqEntity> search(KeySearchMauToKhaiHq objInput) {
		return convertListObject2PageOfObject(excuteListObjectUsingSp( //
				MauToKhaiHqEntity.class //
				, "PKG_MAU_TO_KHAI_HQ.search" //
				, objInput.getLoaiTkHq() //
				, objInput.getNhomLoaiHinh() //
				, objInput.getNgayTaoFrom() //
				, objInput.getNgayTaoTo() //
				, objInput.getSoTiepNhan() //
				, objInput.getMaHq() //
				, objInput.getMaLoaiHinh() //
				, objInput.getPhanLuong() //
				, objInput.getSoTk() //
				, objInput.getToKhaiNhapXuat()
				, objInput.getCode()
	    		, objInput.getPageBegin() //
				, objInput.getPageEnd()), 
		    	PageRequest.of(objInput.getPage(), objInput.getSize())
	    	);
	}

	public List<KeyValueDto> selectSoTiepNhan(Integer soTiepNhan, String loaiTkHq) {
		 return excuteListObjectUsingSp(
		 		 KeyValueDto.class
				,"PKG_MAU_TO_KHAI_HQ.selectSoTiepNhan"
				,soTiepNhan
				 ,loaiTkHq
		);
	}

	public List<KeyValueDto> selectSoTk(Integer soTk,String loaiTkHq) {
		return excuteListObjectUsingSp(
				KeyValueDto.class
				, "PKG_MAU_TO_KHAI_HQ.selectSoTk"
				, soTk
				, loaiTkHq
		);
	}
}
