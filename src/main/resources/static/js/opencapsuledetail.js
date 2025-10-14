
const afileList =
	JSON.parse($('#my_capsule_detail')
		.data('afiles'));

let currIdx = 0;

const preBtn = $('#pre');
const nextBtn = $('#next');

let imgField = $('#image');
let videoField = $('#video');

$(function() {

	if (afileList.length == 0) {
		// 파일이 하나이면 이전/다음 버튼 안 보이게
		$('.uploaded_image_or_video').hide();
	} else {

		let firstFileResource = afileList[0].afcontenttype;

		// 첫 번째 이미지(or 동영상) 보여줌
		if (firstFileResource.startsWith('image')) {
			showImage(firstFileResource);
		} else if (firstFileResource.startsWith('video')) {
			showVideo(firstFileResource);
		}


		preBtn.on('click', () => {
			renderAccordingToAction('pre');
		});

		nextBtn.on('click', () => {
			renderAccordingToAction('next');
		});
	}
	
	
	if()
	

});


function renderAccordingToAction(action) {

	switch (action) {
		case 'pre':

			currIdx--;
			const preIdxObj = afileList[currIdx];

			if (preIdxObj.afcontenttype.startsWith('image')) {
				imgField.attr('src', preIdxObj.afsname);

			} else if (preIdxObj.afcontenttype.startsWith('video')) {
				videoField.attr('src', preIdxObj.afsname);
			}

			break;

		case 'next':
			
			currIdx++;
			let nextIdxObj = afileList[currIdx];

			if (nextIdxObj.afcontenttype.startsWith('image')) {
				showImage(nextIdxObj.afsname);
			} else if (nextIdxObj.afcontenttype.startsWith('video')) {
				showVideo(nextIdxObj.afsname);
			}

			break;
	}
}


function showVideo(filePath) {

	let source = $('#source');

	source.attr('src', filePath);

	if (filePath.endsWith('mp4')) {
		source.attr('type', 'video/mp4');
	} else if (filePath.endsWith('webm')) {
		source.attr('type', 'video/webm')
	}

}


function showImage(filePath) {
	let img = $('#image');
	img.attr('src', filePath);
}
