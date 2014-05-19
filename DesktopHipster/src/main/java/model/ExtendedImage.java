package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import filter.FiltersEnum;

/**
 * Image class extending BufferedImage for having preview and thumbnail sized
 * versions.
 * 
 * @author Edvard Hubinette
 * @revised Simon Arneson
 * @revised Edvard Hübinette
 * @revised Lovisa Jäberg
 */
public class ExtendedImage implements ThumbnailData, Serializable {

	private static final long serialVersionUID = -3976848293951144039L;
	private static int id;
	private int imageID;

	private transient TreeSet<String> tags = new TreeSet<String>();
	private transient BufferedImage preview;
	private transient BufferedImage thumbnail;
	private transient BufferedImage original;

	private transient Map<FiltersEnum, BufferedImage> versions = new HashMap<FiltersEnum, BufferedImage>();

	/**
	 * For use of the java serialization engine.
	 */
	public ExtendedImage() {
	}

	/**
	 * Generates a new ExtendedImage from an
	 * 
	 * @param image
	 *            The original image to represent
	 */
	public ExtendedImage(final ImageIcon image) {
		if (image == null) {
			throw new IllegalArgumentException("Supplied image is null");
		}
		original = new BufferedImage(image.getIconWidth(),
				image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics graphics = original.createGraphics();
		image.paintIcon(null, graphics, 0, 0);
		graphics.dispose();

		preview = filter.ImageTools.toBufferedImage(original.getScaledInstance(
				750, -1, Image.SCALE_SMOOTH));
		thumbnail = filter.ImageTools.toBufferedImage(original
				.getScaledInstance(50, -1, Image.SCALE_FAST));
		imageID = id += 1;
	}

	/**
	 * Adds an edited version of this image to a list of versions
	 * 
	 * @param filter
	 *            The name of the filter used
	 * @param image
	 *            The edited image to stash
	 */
	public void addVersion(final FiltersEnum filter, final BufferedImage image) {
		versions.put(filter, image);
	}

	/**
	 * Gets a version of the image with a specific filter
	 * 
	 * @param filterName
	 *            The name of the filter used
	 * @return The edited image to stash
	 * @throws NoSuchVersionException
	 *             Image version with that filter does not exist
	 */
	public BufferedImage getVersion(final FiltersEnum filterName)
			throws NoSuchVersionException {
		if (filterName != null && versions.containsKey(filterName)) {
			return versions.get(filterName);
		} else {
			throw new NoSuchVersionException();
		}
	}

	/**
	 * Returns a smaller version of the image
	 * 
	 * @return Preview-sized version of this image
	 */
	public BufferedImage getPreview() {
		return preview;
	}

	/**
	 * Set a new preview-sized representation of this image.
	 * 
	 * @param newPreview
	 *            The new image to use as preview
	 */
	public void setPreview(final BufferedImage newPreview) {
		preview = newPreview;
	}

	/**
	 * Returns a small version of the image
	 * 
	 * @return Thumbnail-sized version of this image
	 */
	public BufferedImage getThumbnail() {
		return thumbnail;
	}

	/**
	 * Returns the original version of the image
	 * 
	 * @return Full-sized version of the original image
	 */
	public BufferedImage getOriginal() {
		return original;
	}

	/**
	 * Returns a list of all versions of this image
	 */
	@Override
	public List<BufferedImage> getVersions() {
		return new ArrayList<BufferedImage>(versions.values());
	}

	/**
	 * Get the sorted set of all tags applied to this image.
	 */
	@Override
	public TreeSet<String> getTags() {
		return new TreeSet<String>(tags);
	}

	/**
	 * Adds a tag to this image. If it exists, nothing happens.
	 * 
	 * @param tag
	 *            The tag to add
	 * @return True if tag was added, false if not.
	 */
	public boolean addTag(String tag) {
		if (tags.contains(tag)) {
			return false;
		} else {
			tags.add(tag);
		}
		return true;
	}

	/**
	 * Remove a tag from this image. If it does not exist, nothing happens.
	 * 
	 * @param tag
	 *            The tag to remove
	 */
	public void removeTag(final String tag) {
		tags.remove(tag);
	}

	/**
	 * Get the thumbnail representation of this image
	 */
	@Override
	public BufferedImage getSelectedVersion() {
		return thumbnail;
	}

	/**
	 * Set a new size for the thumbnail image representation
	 * 
	 * @param width
	 *            The wanted width
	 */
	public void setThumbnailSize(final int width) {
		thumbnail = filter.ImageTools.toBufferedImage(original
				.getScaledInstance(width, width, Image.SCALE_FAST));
	}

	/**
	 * Returns an unique ID for this image
	 * 
	 * @return The image ID
	 */
	public int getID() {
		return imageID;
	}

	/**
	 * Manual enabling of serialization for java object streams
	 * 
	 * @param stream
	 *            Associated output stream
	 * @throws IOException
	 */
	private void writeObject(final ObjectOutputStream stream)
			throws IOException {
		stream.defaultWriteObject();
		ImageIO.write(original, "png", stream);

		stream.writeObject(tags);

		stream.writeInt(versions.size());
		for (final Entry<FiltersEnum, BufferedImage> entry : versions
				.entrySet()) {
			stream.writeObject(entry.getKey());
			ImageIO.write(entry.getValue(), "png", stream);
		}
	}

	/**
	 * Manual enabling of deserialization for java object streams
	 * 
	 * @param stream
	 *            Associated input stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(final ObjectInputStream stream) throws IOException,
			ClassNotFoundException {
		stream.defaultReadObject();
		original = ImageIO.read(stream);
		preview = filter.ImageTools.toBufferedImage(original.getScaledInstance(
				750, -1, Image.SCALE_FAST));
		thumbnail = filter.ImageTools.toBufferedImage(original
				.getScaledInstance(50, -1, Image.SCALE_FAST));

		/*
		 * I have no idea how this works, but in the stream we encounter four
		 * separate, apparently random, 32-bit ints that come from nowhere, I do
		 * not write them to the stream nor can I find the source in any
		 * sun/oracle serialization documentation. After extensive debugging,
		 * the serialization works with this configuration, on other clients as
		 * well.
		 */
		stream.readInt();stream.readInt();stream.readInt();stream.readInt();
		
		tags = (TreeSet<String>) stream.readObject();

		versions = new TreeMap<FiltersEnum, BufferedImage>();
		
		int len = stream.readInt();
		try {
			for (int i = 0; i < len; i++) {
				final FiltersEnum key = ((FiltersEnum) stream.readObject());
				versions.put(key, ImageIO.read(stream));
			}
		} catch (OptionalDataException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a human-readable string representation of this instance
	 */
	@Override
	public String toString() {
		return "ExtendedImage [" + "imageID=" + imageID + ", " + "tags="
				+ tags.size() + ", " + "versions=" + versions.size() + "]";
	}

	/**
	 * Generates a hash code for this instance
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + imageID;
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result
				+ ((versions == null) ? 0 : versions.hashCode());
		return result;
	}

	/**
	 * Ensures, beyond reasonable doubt, that two instances of this class are
	 * the identical.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExtendedImage other = (ExtendedImage) obj;
		if (imageID != other.imageID) {
			return false;
		}
		if (tags == null) {
			if (other.tags != null) {
				return false;
			}
		} else if (!tags.equals(other.tags)) {
			return false;
		}
		if (versions == null) {
			if (other.versions != null) {
				return false;
			}
		} else if (!versions.equals(other.versions)) {
			return false;
		}
		return true;
	}
}
