DESCRIPTION = "software media player and entertainment hub"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://copying.txt;md5=4cea88e622381d1ad6e43f07c47513b5"
DEPENDS = "mysql5 libsamplerate0 alsa-lib udev libvorbis boost libass mpeg2dec libmad libmodplug tiff yajl libtinyxml taglib libcdio jasper libmicrohttpd libssh samba rtmpdump libnfs samba swig-native libxslt libplist shairplay flac"
DEPENDS_append_mx6 = " virtual/kernel virtual/libgles2 virtual/egl libfslvpuwrap libcec"

SRC_URI = "git://github.com/rdm-dev/xbmc.git;rev=${SRCREV} \
           file://vp8ts.patch \
	   file://rss.patch \
	"
SRCREV="dc641448862fe68dce250fd8808b54b7b7af2a12"

INC_PR = "r1"
PR = "${INC_PR}.2"

S = "${WORKDIR}/git"

inherit autotools lib_package pkgconfig gettext python-dir record-installed-app

EXTRA_OECONF="ac_cv_path_JAVA_EXE=/usr/bin/java \
--prefix=/imx6/xbmc --enable-external-ffmpeg --disable-x11 --disable-sdl --disable-xrandr --disable-gl --disable-vdpau --disable-vaapi --disable-openmax --enable-neon --enable-gles --enable-udev --enable-codec=imxvpu --disable-debug --disable-texturepacker --enable-airplay --enable-airtunes"

CPPFLAGS += " -I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include "
CXXFLAGS += " -I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include "
CFLAGS += " -I${STAGING_KERNEL_DIR}/include/uapi -I${STAGING_KERNEL_DIR}/include "

do_configure() {
  export PYTHON_EXTRA_LDFLAGS=""
  export PYTHON_EXTRA_LIBS="-lz"
  export PYTHON_VERSION="${PYTHON_BASEVERSION}"
  export PYTHON_NOVERSIONCHECK="no-check"
  export PYTHON_CPPFLAGS="-I/${STAGING_INCDIR}/${PYTHON_DIR}"
  export PYTHON_LDFLAGS="-L${STAGING_LIBDIR} -lpython${PYTHON_BASEVERSION}"

  #We will use the host java during build because there is no native recipe for full openjdk and jamvm is not able to build xbmc
  export JAVA="/usr/bin/java"

  rm -rf ${S}/addons/service.xbmc.versioncheck

  sh bootstrap
  # oe_runconf
  # Call manually configure instead of oe_runconf because of specific prefix /imx6/xbmc...
  ${S}/configure  --build=${BUILD_SYS} \
                  --host=${HOST_SYS} \
                  --target=${TARGET_SYS} \
                  ${@append_libtool_sysroot(d)} \
                  ${EXTRA_OECONF}
}

# XBMC plugins requires python modules and dynamically loaded libraries
RDEPENDS_${PN} = " python-stringold \
                   python-mime \
                   python-logging \
                   python-crypt \
                   python-netclient \
                   python-threading \
                   python-elementtree \
                   python-xml \
                   python-dbus \
                   python-html \
                   python-netserver \
                   python-zlib \
                   python-sqlite3 \
                   python-pydoc \
                   python-textutils \
                   python-shell \
                   python-image \
                   python-robotparser \
                   python-compression \
                   python-audio \
                   python-email \
                   python-numbers \
                   python-json \
                   python-subprocess \
                   python-xmlrpc \
                   python-misc \
                   python-modules \
                   eglibc-gconv-cp1252 \
                   libcurl \
                   libnfs \
                   rtmpdump \
                   upower \
                   libmad \
                   libass \
                   mpeg2dec \
                   libcec \
                   kernel \
                   eglibc-gconv-ibm850 \
                   eglibc-gconv-utf-32 \
                   jasper \
                   udev \
                   alsa-lib \
                   alsa-conf \
                   tzdata \
                   xbmc-pvr-addons \
                   shairplay \
                   libvorbis \
                   flac \
                 "

FILES_${PN} += "/imx6 /usr/share/icons/hicolor"
FILES_${PN}-dbg += " \
                    /imx6/xbmc/lib/xbmc/system/players/paplayer/.debug/ \
                    /imx6/xbmc/lib/xbmc/system/players/dvdplayer/.debug/ \
                    /imx6/xbmc/lib/xbmc/system/.debug/ \
                    /imx6/xbmc/lib/xbmc/addons/library.xbmc.gui/.debug/ \
                    /imx6/xbmc/lib/xbmc/addons/library.xbmc.addon/.debug/ \
                    /imx6/xbmc/lib/xbmc/addons/visualization.glspectrum/.debug/ \
                    /imx6/xbmc/lib/xbmc/addons/library.xbmc.pvr/.debug/ \
                    /imx6/xbmc/lib/xbmc/addons/visualization.waveform/.debug/ \
                    /imx6/xbmc/lib/xbmc/.debug/ \
                    /imx6/xbmc/lib/xbmc/addons/library.xbmc.codec/.debug \
                   "
