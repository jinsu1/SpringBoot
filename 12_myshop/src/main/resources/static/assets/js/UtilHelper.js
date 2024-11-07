/**
 * UtilHelper.js
 * 
 * 재사용 가능한 기능들을 모아 놓은 클래스
 */
class UtilHelper {
    colorMap = {
        primary: "#0D6EFD",
        success: "#198754",
        danger: "#DC3545",
        warning: "#FFC107",
        info: "#0DCAF0",
        light: "#F8F9FA",
        dark: "#212529",
    }


    /**
     * URL의 querystring을 JSON객체로 변환하여 리턴한다.
     * @returns json object
     */

    getQuery() {
        const query = new URLSearchParams(location.search);
        return Object.fromEntries(query);
    }

    /**
     * 쿠키에 저장된 값을 반환한다. 값이 없을 경우 undefined를 반환한다.
     * @param {string} name  - 쿠키의 이름
     * @returns 쿠키값
     */
    getCookie(name) {
        const regex = new RegExp("(?:^|; )" + name.replace(/([\.$?*|{}\(\)|[|]\\\/\+^])/g, "\\$1") + "=([^;]*)");
        let matches = document.cookie.match(regex);
        return matches ? decodeURIComponent(matches[1]) : undefined;
    }
   
    /**
     * 쿠키를 저장한다.
     * @param {string} name - 쿠키 이름
     * @param {*} value - 저장할 값
     * @param {number} maxAge - 유효시간(초단위)
     */
    setCookie(name, value, maxAge) {
        const encName = encodeURIComponent(name);
        const encValue = encodeURIComponent(value);
        let updatedCookie = `${encName}=${encValue};`;

        //유효경로 설정
        updatedCookie += "path=/;";
        //updatedCookie = "domain=.naver.com" 쿠키 전역 설정

        //유효시간은 파라미터가 전달된 경우만 설정
        //파라미터가 전달 안된 경우 설정되지 않기 때문에 브라우저를 닫기 전까지 유지
        if(maxAge !== undefined) {
            updatedCookie += `max-age=${maxAge}`;
        }

        //저장
        document.cookie = updatedCookie;
    }

    findPostCode(postCodeField = "#postcode", addr1Field = "#addr1", addr2Field = "#addr2") {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    addr += extraAddr;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.querySelector(postCodeField).value = data.zonecode;
                document.querySelector(addr1Field).value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.querySelector(addr2Field).focus();
            },
        }).open();
    }

    async alert(title, text, icon, confirmButtonText, confirmButtonColor) {
        if(Swal !== undefined) {

            if (text === undefined) {
                text = title;
                title = "알림";
            }

            return await Swal.fire({
                title: title,
                text: text,
                icon: icon,
                showCloseButton: true,
                confirmButtonText: confirmButtonText,
                confirmButtonColor: confirmButtonColor,
                showCancelButton: false,
            });
        } else {
            this.alert(text);
        }
    }

    async alertPrimary(title, text = undefined) {
        return await this.alert(title, text, "info", "확인", this.colorMap.primary);
    }

    async alertSuccess(title, text = undefined) {
        return await this.alert(title, text, "success", "확인", this.colorMap.success);
    }

    async alertDanger(title, text = undefined) {
        return await this.alert(title, text, "error", "확인", this.colorMap.danger);
    }

    async alertWarning(title, text = undefined) {
        return await this.alert(title, text, "warning", "확인", this.colorMap.warning);
    }

    async alertInfo(title, text = undefined) {
        return await this.alert(title, text, "info", "확인", this.colorMap.info);
    }

    async confirm(title, text, confirmButtonText, confirmButtonColor, cancelButtonText, cancelButtonColor) {
        if(Swal !== undefined) {
            return await Swal.fire({
                title: title,
                text: text,
                icon: "question",
                showCloseButton: true,
                confirmButtonText: confirmButtonText,
                confirmButtonColor: confirmButtonColor,
                showCancelButton: true,
                cancelButtonText: cancelButtonText,
                cancelButtonColor: cancelButtonColor,
            });
        } else {
            return this.confirm(text);
        }
    }

    async confirmPrimary(title, text, confirmButtonText = "확인", cancelButtonText = "취소") {
        return await this.confirm(title, text, confirmButtonText, this.colorMap.primary, cancelButtonText, this.colorMap.light);
    }

    async confirmPSuccess(title, text, confirmButtonText = "확인", cancelButtonText = "취소") {
        return await this.confirm(title, text, confirmButtonText, this.colorMap.success, cancelButtonText, this.colorMap.light);
    }

    async confirmDanger(title, text, confirmButtonText = "확인", cancelButtonText = "취소") {
        return await this.confirm(title, text, confirmButtonText, this.colorMap.danger, cancelButtonText, this.colorMap.light);
    }

    async confirmWarning(title, text, confirmButtonText = "확인", cancelButtonText = "취소") {
        return await this.confirm(title, text, confirmButtonText, this.colorMap.warning, cancelButtonText, this.colorMap.light);
    }

    async confirmInfo(title, text, confirmButtonText = "확인", cancelButtonText = "취소") {
        return await this.confirm(title, text, confirmButtonText, this.colorMap.info, cancelButtonText, this.colorMap.light);
    }
}
const utilHelper = new UtilHelper();