var PhotoService = function () {
  return {
    getPhotoUrl: function(baseUrl, width) {
      var photoSizeName = PHOTO_SIZES[0].name;
      for (var i = PHOTO_SIZES.length - 1; i > 0; i--) {
        if (width > PHOTO_SIZES[i - 1].width) {
          photoSizeName = PHOTO_SIZES[i].name;
          break;
        }
      }
      return baseUrl + photoSizeName + ".jpeg";
    }
  }
};