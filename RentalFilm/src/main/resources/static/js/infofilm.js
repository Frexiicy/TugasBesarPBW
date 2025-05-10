$(document).ready(function() {

	let str = $('#synopsis').text();
	
	if (str.length > 200) { /* Jika teks lebih dari 200 karakter akan dipotong dan ditambahkan "Read more" */
		$('#synopsis').empty().html(str.substr(0,200));
		let moreLink = '<a href="#" class="read_more"> Read more</a>';
		$('#synopsis').append(moreLink);
		
		$('#synopsis').on('click', '.read_more', function(e) { /* Jika "Read more diklik, maka akan menampilkan seluruh sinopsis" */
			e.preventDefault();
			$('#synopsis').empty().html(str);
            let lessLink = '<a href="#" class="read_less"> Read less</a>';
            $('#synopsis').append(lessLink); /* dan memunculkan "Read less" */
		});
        
        $('#synopsis').on('click', '.read_less', function(e){ /* Jika "Read less" diklik, maka sinopsis akan dipotong menjadi 200 karakter */
            e.preventDefault();
            $('#synopsis').empty().html(str.substr(0,200));
            let moreLink = '<a href="#" class="read_more"> Read more</a>';
            $('#synopsis').append(moreLink);
        });
	}
	
	function getQueryParam(param){
		let urlParam = new URLSearchParams(window.location.search);
		return urlParam.get(param);
	}
	
	let status = getQueryParam("status");
	if(status === "success"){
		$('#success').removeAttr("hidden");
	}else if(status === "failure"){
		$('#failure').removeAttr("hidden");
	}
});